package io.choerodon.gitlab.app.service.impl;

import io.choerodon.core.exception.FeignException;
import io.choerodon.gitlab.api.vo.GroupVO;
import io.choerodon.gitlab.api.vo.MemberVO;
import io.choerodon.gitlab.api.vo.VariableVO;
import io.choerodon.gitlab.app.service.GroupService;
import io.choerodon.gitlab.infra.common.client.Gitlab4jClient;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.GroupApi;
import org.gitlab4j.api.models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GroupServiceImpl implements GroupService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupServiceImpl.class);
    private Gitlab4jClient gitlab4jclient;

    public GroupServiceImpl(Gitlab4jClient gitlab4jclient) {
        this.gitlab4jclient = gitlab4jclient;
    }

    @Override
    public GroupVO createGroup(GroupVO group, Integer userId) {
        GitLabApi gitLabApi = gitlab4jclient.getGitLabApi(userId);
        try {
            GroupApi groupApi = gitLabApi.getGroupApi();
            groupApi.addGroup(group.getName(), group.getPath(), group.getDescription(),
                    null, null, group.getVisibility(), null,
                    group.getRequestAccessEnabled(), group.getParentId(), group.getSharedRunnersMinutesLimit());
            Group group1 = gitLabApi.getGroupApi().getGroup(group.getPath());
            GroupVO groupVO = new GroupVO();
            BeanUtils.copyProperties(group1, groupVO);
            return groupVO;
        } catch (GitLabApiException e) {
            if (e.getHttpStatus() == 404) {
                try {
                    GroupVO groupVO = new GroupVO();
                    Group group1 = gitLabApi.getGroupApi().getGroup(group.getPath());
                    BeanUtils.copyProperties(group1, groupVO);
                    return groupVO;
                } catch (GitLabApiException e1) {
                    LOGGER.info(e.getMessage());
                    throw new FeignException(e.getMessage(), e);
                }
            }
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public Group updateGroup(Integer groupId, Integer userId, Group group) {
        GitLabApi gitLabApi = gitlab4jclient.getGitLabApi(userId);
        try {
            return gitLabApi.getGroupApi().updateGroup(groupId, group.getName(), group.getPath(),
                    group.getDescription(), null, null, group.getVisibility(), null,
                    group.getRequestAccessEnabled(), group.getParentId(), group.getSharedRunnersMinutesLimit());
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteGroup(Integer groupId, Integer userId) {
        GitLabApi gitLabApi = gitlab4jclient.getGitLabApi(userId);
        GroupApi groupApi = gitLabApi.getGroupApi();
        try {
            User user = gitLabApi.getUserApi().getUser(userId);
            List<Member> members = groupApi.getMembers(groupId)
                    .stream().filter(t -> user.getUsername().equals(t.getUsername())).collect(Collectors.toList());
            if (members != null && AccessLevel.OWNER.value.equals(members.get(0).getAccessLevel().value)) {
                groupApi.deleteGroup(groupId);
            } else {
                throw new FeignException("error.groups.deleteGroup.Owner");
            }
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e);
        }
    }


    @Override
    public List<Project> listProjects(Integer groupId, Integer userId, Integer page, Integer perPage) {
        GitLabApi gitLabApi = gitlab4jclient.getGitLabApi(userId);
        try {
            return gitLabApi.getGroupApi().getProjects(groupId, page, perPage);
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public Group queryGroupByName(String groupName, Integer userId) {
        GitLabApi gitLabApi = gitlab4jclient.getGitLabApi(userId);
        try {
            return gitLabApi.getGroupApi().getGroup(groupName);
        } catch (GitLabApiException e) {
            return null;
        }
    }

    @Override
    public List<AccessRequest> listAccessRequests(Integer groupId) {
        GitLabApi gitLabApi = gitlab4jclient.getGitLabApi();
        try {
            return gitLabApi.getGroupApi().getAccessRequests(groupId);
        } catch (GitLabApiException e) {
            LOGGER.info("ex: {}", e);
            return null;
        }
    }

    @Override
    public void denyAccessRequest(Integer groupId, Integer userIdToBeDenied) {
        GitLabApi gitLabApi = gitlab4jclient.getGitLabApi();
        try {
            gitLabApi.getGroupApi().denyAccessRequest(groupId, userIdToBeDenied);
        } catch (GitLabApiException e) {
            if ("404 Not found".equals(e.getMessage())) {
                LOGGER.info("Swallow not found access request exception...");
            } else {
                LOGGER.info("Swallow exception when denying access request of group id {}, userIdToBeDenied {}", groupId, userIdToBeDenied);
                LOGGER.info("The exception is {}", e);
            }
        }
    }

    @Override
    public List<Group> listGroups() {
        GitLabApi gitLabApi = gitlab4jclient.getGitLabApi();
        try {
            return gitLabApi.getGroupApi().getGroups();
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public List<Group> listGroupsWithParam(Integer userId, Boolean owned, String search, List<Integer> skipGroups) {
        GitLabApi gitLabApi = gitlab4jclient.getGitLabApi(userId);
        try {
            GroupFilter groupFilter = new GroupFilter();
            groupFilter.withSkipGroups(skipGroups).withOwned(owned).withSearch(search);
            return gitLabApi.getGroupApi().getGroups(groupFilter, 20).page(1);
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public List<Member> listMember(Integer groupId) {
        GitLabApi gitLabApi = gitlab4jclient.getGitLabApi();
        try {
            return gitLabApi.getGroupApi().getMembers(groupId);
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public Member queryMemberByUserId(Integer groupId, Integer userId) {
        GitLabApi gitLabApi = gitlab4jclient.getGitLabApi();
        try {
            return gitLabApi.getGroupApi().getMember(groupId, userId);
        } catch (GitLabApiException e) {
            return new Member();
        }
    }

    @Override
    public Member createMember(Integer groupId, MemberVO member) {
        GitLabApi gitLabApi = gitlab4jclient.getGitLabApi();
        try {
            return gitLabApi.getGroupApi().addMember(groupId, member.getId(), member.getAccessLevel(),
                    member.getExpiresAt());
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public Member updateMember(Integer groupId, MemberVO member) {
        GitLabApi gitLabApi = gitlab4jclient.getGitLabApi();
        try {
            return gitLabApi.getGroupApi().updateMember(groupId, member.getId(), member.getAccessLevel(),
                    member.getExpiresAt());
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteMember(Integer groupId, Integer userId) {
        GitLabApi gitLabApi = gitlab4jclient.getGitLabApi();
        try {
            gitLabApi.getGroupApi().removeMember(groupId, userId);
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public List<Variable> getGroupVariable(Integer groupId, Integer userId) {
        try {
            return gitlab4jclient.getGitLabApi(userId).getGroupApi().getVariables(groupId);
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public Variable createVariable(Integer groupId, String key, String value, boolean protecteds, Integer userId) {
        try {
            return gitlab4jclient.getGitLabApi(userId).getGroupApi().createVariable(groupId, key, value, protecteds);
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteVariable(Integer groupId, String key, Integer userId) {
        try {
            gitlab4jclient.getGitLabApi(userId).getGroupApi().deleteVariable(groupId, key);
        } catch (GitLabApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void batchDeleteVariable(Integer groupId, List<String> keys, Integer userId) {
        keys.forEach(key -> {
            deleteVariable(groupId, key, userId);
        });
    }

    @Override
    public List<Variable> batchCreateVariable(Integer groupId, List<VariableVO> list, Integer userId) {
        List<Variable> oldlist = getGroupVariable(groupId, userId);
        return list.stream().filter(t -> t.getValue() != null).map(v -> {
            try {
                String key = v.getKey();
                Optional<Variable> optional = oldlist.stream().filter(t -> key.equals(t.getKey())).findFirst();
                if (optional.isPresent() && !optional.get().getKey().isEmpty()) {
                    return gitlab4jclient.getGitLabApi(userId)
                            .getGroupApi().updateVariable(groupId, v.getKey(), v.getValue(), false);
                } else {
                    return gitlab4jclient.getGitLabApi(userId)
                            .getGroupApi().createVariable(groupId, v.getKey(), v.getValue(), false);
                }
            } catch (GitLabApiException e) {
                throw new FeignException(e.getMessage(), e);
            }
        }).collect(Collectors.toList());
    }

    @Override
    public List<Project> listProjects(Integer groupId, Integer userId, Boolean owned, String search, Integer page, Integer perPage) {
        GitLabApi gitLabApi = gitlab4jclient.getGitLabApi(userId);
        try {
            GroupProjectsFilter groupProjectsFilter = new GroupProjectsFilter();
            groupProjectsFilter.withOwned(owned).withSearch(search).withPage(page).withPerPage(perPage);
            return gitLabApi.getGroupApi().getProjects(groupId, groupProjectsFilter);
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e);
        }
    }
}