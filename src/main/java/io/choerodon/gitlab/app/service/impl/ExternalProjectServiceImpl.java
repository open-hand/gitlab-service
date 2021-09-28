package io.choerodon.gitlab.app.service.impl;

import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Project;
import org.springframework.stereotype.Service;

import io.choerodon.core.exception.CommonException;
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
    public Project queryProjectByCode(String namespaceCode, String projectCode, AppExternalConfigDTO appExternalConfigDTO) {
        try {
            return ExternalGitlabApiUtil.createGitLabApi(appExternalConfigDTO).getProjectApi().getProject(namespaceCode, projectCode);
        } catch (GitLabApiException e) {
            throw new CommonException("error.query.project.failed", e);
        }

    }
}
