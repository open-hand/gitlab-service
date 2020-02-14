package io.choerodon.gitlab.app.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import io.choerodon.core.exception.FeignException;
import io.choerodon.gitlab.api.vo.MemberVO;
import io.choerodon.gitlab.api.vo.VariableVO;
import io.choerodon.gitlab.app.service.ProjectService;
import io.choerodon.gitlab.infra.common.client.Gitlab4jClient;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectServiceImpl.class);
    Gson gson = new Gson();
    private Gitlab4jClient gitlab4jclient;

    public ProjectServiceImpl(Gitlab4jClient gitlab4jclient) {
        this.gitlab4jclient = gitlab4jclient;
    }

    @Override
    public Project createProject(Integer groupId, String projectName, Integer userId, boolean visibility) {
        GitLabApi gitLabApi = gitlab4jclient.getGitLabApi(userId);
        try {
            Project projectReq = new Project();
            projectReq.setName(projectName);
            projectReq.setPath(projectName);
            Namespace namespace = new Namespace();
            namespace.setId(groupId);
            projectReq.setNamespace(namespace);
            Project project = gitLabApi.getProjectApi().createProject(projectReq);
            if (visibility) {
                project.setVisibility(Visibility.PUBLIC);
            }
            project.setPublic(true);
            return gitLabApi.getProjectApi().updateProject(project);
        } catch (GitLabApiException e) {
            LOGGER.info("groupId:{},projectName:{},userId:{},visibility:{}", groupId, projectName, userId, visibility);
            LOGGER.info("{}", e.getMessage());
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteProject(Integer projectId, Integer userId) {
        try {
            gitlab4jclient
                    .getGitLabApi(userId)
                    .getProjectApi().deleteProject(projectId);
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteProjectByName(String groupName, String projectName, Integer userId) {
        try {
            Project project = null;
            try {
                project = gitlab4jclient
                        .getGitLabApi(userId)
                        .getProjectApi().getProject(groupName, projectName);
            } catch (GitLabApiException e) {
                if (e.getHttpStatus() == 404) {
                    LOGGER.info("delete not exist project: {}", e.getMessage());
                } else {
                    throw e;
                }
            }
            if (project != null) {
                gitlab4jclient
                        .getGitLabApi(userId)
                        .getProjectApi().deleteProject(project.getId());
            }
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public Project getProjectById(Integer projectId) {
        try {
            return gitlab4jclient.getGitLabApi().getProjectApi().getProject(projectId);
        } catch (GitLabApiException e) {
            if (e.getHttpStatus() == 404) {
                return new Project();
            }
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public Project getProject(Integer userId, String groupCode, String projectCode) {
        try {
            String targetPathWithNamespace = groupCode + "/" + projectCode;
            return gitlab4jclient.getGitLabApi(userId).getProjectApi().getProjects(projectCode).stream()
                    .filter(i -> i.getPathWithNamespace().equals(targetPathWithNamespace))
                    .findFirst().orElse(new Project());
        } catch (GitLabApiException e) {
            if (e.getHttpStatus() == 404) {
                return new Project();
            } else {
                throw new FeignException(e.getMessage(), e);
            }
        }
    }

    @Override
    public List<Variable> getVarible(Integer projectId, Integer userId) {
        try {
            return gitlab4jclient.getGitLabApi(userId).getProjectApi().getVariable(projectId);
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public Map<String, Object> createVariable(Integer projectId, String key, String value, boolean protecteds,
                                              Integer userId) {
        try {
            return gitlab4jclient.getGitLabApi(userId)
                    .getProjectApi().addVariable(projectId, key, value, protecteds);
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public List<Map<String, Object>> batchCreateVariable(Integer projectId, List<VariableVO> list, Integer userId) {
        List<Variable> oldlist = getVarible(projectId, userId);
        return list.stream().filter(t -> t.getValue() != null).map(v -> {
            try {
                String key = v.getKey();
                Optional<Variable> optional = oldlist.stream().filter(t -> key.equals(t.getKey())).findFirst();
                if (optional.isPresent() && !optional.get().getKey().isEmpty()) {
                    return gitlab4jclient.getGitLabApi(userId)
                            .getProjectApi().updateVariable(projectId, v.getKey(), v.getValue(), v.getProtecteds());
                } else {
                    return gitlab4jclient.getGitLabApi(userId)
                            .getProjectApi().addVariable(projectId, v.getKey(), v.getValue(), v.getProtecteds());
                }
            } catch (GitLabApiException e) {
                throw new FeignException(e.getMessage(), e);
            }
        }).collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> createProtectedBranches(Integer projectId, String name, String mergeAccessLevel,
                                                       String pushAccessLevel, Integer userId) {
        try {
            return gitlab4jclient.getGitLabApi(userId)
                    .getProjectApi().protectedBranches(projectId, name, mergeAccessLevel, pushAccessLevel);
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public Project updateProject(Project newProject, Integer userId) {
        try {
            Project project = gitlab4jclient.getGitLabApi().getProjectApi().getProject(newProject.getId());
            return gitlab4jclient.getGitLabApi(userId)
                    .getProjectApi().updateProject(project);
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public Map<String, Object> queryBranchByBranchName(Integer id, String name, Integer userId) {
        try {
            return gitlab4jclient.getGitLabApi(userId)
                    .getProjectApi().getBranch(id, name);
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public List<Map<String, Object>> listBranch(Integer id, Integer userId) {
        try {
            return gitlab4jclient.getGitLabApi(userId)
                    .getProjectApi().getBranchs(id);
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteByBranchName(Integer id, String name, Integer userId) {
        try {
            gitlab4jclient.getGitLabApi(userId)
                    .getProjectApi().deleteBranch(id, name);
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public void createDeployKey(Integer projectId, String title, String key, boolean canPush, Integer userId) {
        try {
            gitlab4jclient.getGitLabApi(userId).getDeployKeysApi().addDeployKey(projectId, title, key, canPush);
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public List<DeployKey> getDeployKeys(Integer projectId, Integer userId) {
        try {
            return gitlab4jclient.getGitLabApi(userId).getDeployKeysApi().getProjectDeployKeys(projectId);
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public Member createMember(Integer projectId, MemberVO member) {
        try {
            return gitlab4jclient.getGitLabApi().getProjectApi()
                    .addMember(projectId, member.getId(), member.getAccessLevel());
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public List<Member> updateMembers(Integer projectId, List<MemberVO> list) {
        return list.stream().map(m -> {
            try {
                return gitlab4jclient.getGitLabApi().getProjectApi()
                        .updateMember(projectId, m.getId(), m.getAccessLevel());
            } catch (GitLabApiException e) {
                throw new FeignException(e.getMessage(), e);
            }
        }).collect(Collectors.toList());
    }

    @Override
    public void deleteMember(Integer projectId, Integer userId) {
        try {
            gitlab4jclient.getGitLabApi().getProjectApi().removeMember(projectId, userId);
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public Member getMember(Integer projectId, Integer userId) {
        try {
            return gitlab4jclient.getGitLabApi().getProjectApi().getMember(projectId, userId);
        } catch (GitLabApiException e) {
            LOGGER.error("no member found");
            Member member = new Member();
            member.setAccessLevel(AccessLevel.NONE);
            return member;
        }
    }

    @Override
    public List<Member> getAllMemberByProjectId(Integer projectId) {
        try {
            return gitlab4jclient.getGitLabApi().getProjectApi().getMembers(projectId);
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public List<Project> getMemberProjects(Integer userId) {
        try {
            return gitlab4jclient.getGitLabApi(userId).getProjectApi().getMemberProjects();
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e);
        }
    }
}