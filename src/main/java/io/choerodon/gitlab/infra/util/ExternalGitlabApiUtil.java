package io.choerodon.gitlab.infra.util;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;

import io.choerodon.core.exception.CommonException;
import io.choerodon.core.exception.FeignException;
import io.choerodon.gitlab.infra.dto.AppExternalConfigDTO;
import io.choerodon.gitlab.infra.enums.ExternalAppAuthTypeEnum;

/**
 * 〈功能简述〉
 * 〈〉
 *
 * @author wanghao
 * @since 2021/9/28 21:37
 */
public class ExternalGitlabApiUtil {

    public static GitLabApi createGitLabApi(AppExternalConfigDTO appExternalConfigDTO) {

        try {
            GitLabApi newGitLabApi;
            if (ExternalAppAuthTypeEnum.ACCESS_TOKEN.getValue().equals(appExternalConfigDTO.getAuthType())) {

                newGitLabApi = new GitLabApi(GitLabApi.ApiVersion.V4, appExternalConfigDTO.getGitlabUrl(), appExternalConfigDTO.getAccessToken());
            } else if (ExternalAppAuthTypeEnum.USERNAME_PASSWORD.getValue().equals(appExternalConfigDTO.getAuthType())) {
                newGitLabApi = GitLabApi.loginWithOAuth(appExternalConfigDTO.getGitlabUrl(), appExternalConfigDTO.getUsername(), appExternalConfigDTO.getPassword());
            } else {
                throw new CommonException("unknown auth type");
            }

            return newGitLabApi;
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e);
        }
    }
}
