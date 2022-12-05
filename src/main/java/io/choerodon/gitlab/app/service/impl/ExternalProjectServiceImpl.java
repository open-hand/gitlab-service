package io.choerodon.gitlab.app.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Project;
import org.gitlab4j.api.models.ProjectHook;
import org.gitlab4j.api.models.Variable;
import org.springframework.stereotype.Service;

import io.choerodon.core.exception.CommonException;
import io.choerodon.core.exception.FeignException;
import io.choerodon.gitlab.api.vo.VariableVO;
import io.choerodon.gitlab.app.service.ExternalProjectService;
import io.choerodon.gitlab.infra.dto.AppExternalConfigDTO;
import io.choerodon.gitlab.infra.util.ExternalGitlabApiUtil;

/**
 * 〈功能简述〉
 * 〈〉
 *
 * @author wanghao
 * @since 2021/9/28 21:35
 */
@Service
public class ExternalProjectServiceImpl implements ExternalProjectService {

    @Override
    public Project queryExternalProjectByCode(String namespaceCode, String projectCode, AppExternalConfigDTO appExternalConfigDTO) {
        try {
            return ExternalGitlabApiUtil.createGitLabApi(appExternalConfigDTO).getProjectApi().getProject(namespaceCode, projectCode);
        } catch (GitLabApiException e) {
            throw new CommonException("error.query.project.failed", e);
        }

    }

    @Override
    public List<Variable> getProjectVariable(Integer projectId, AppExternalConfigDTO appExternalConfigDTO) {
        try {
            return ExternalGitlabApiUtil.createGitLabApi(appExternalConfigDTO).getProjectApi().getVariable(projectId);
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e);
        }
    }


    @Override
    public List<Map<String, Object>> batchCreateVariable(Integer projectId, List<VariableVO> list, AppExternalConfigDTO appExternalConfigDTO) {
        List<Variable> oldlist = getProjectVariable(projectId, appExternalConfigDTO);
        return list.stream().filter(t -> t.getValue() != null).map(v -> {
            try {
                String key = v.getKey();
                Optional<Variable> optional = oldlist.stream().filter(t -> key.equals(t.getKey())).findFirst();
                if (optional.isPresent() && !optional.get().getKey().isEmpty()) {
                    return ExternalGitlabApiUtil.createGitLabApi(appExternalConfigDTO)
                            .getProjectApi().updateVariable(projectId, v.getKey(), v.getValue(), false);
                } else {
                    return ExternalGitlabApiUtil.createGitLabApi(appExternalConfigDTO)
                            .getProjectApi().addVariable(projectId, v.getKey(), v.getValue(), false);
                }
            } catch (GitLabApiException e) {
                throw new FeignException(e.getMessage(), e);
            }
        }).collect(Collectors.toList());
    }

    @Override
    public ProjectHook createProjectHook(Integer projectId, ProjectHook projectHook, AppExternalConfigDTO appExternalConfigDTO) {
        try {
            return ExternalGitlabApiUtil.createGitLabApi(appExternalConfigDTO).getProjectApi()
                    .addHook(projectId, projectHook.getUrl(), projectHook, true, projectHook.getToken());
        } catch (Exception e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteHook(Integer projectId, Integer hookId, AppExternalConfigDTO appExternalConfigDTO) {
        try {
            ExternalGitlabApiUtil.createGitLabApi(appExternalConfigDTO).getProjectApi()
                    .deleteHook(projectId, hookId);
        } catch (Exception e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public List<ProjectHook> listProjectHook(Integer projectId, AppExternalConfigDTO appExternalConfigDTO) {
        try {
            return ExternalGitlabApiUtil.createGitLabApi(appExternalConfigDTO).getProjectApi().getHooks(projectId);
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage(),e);
        }
    }

}
