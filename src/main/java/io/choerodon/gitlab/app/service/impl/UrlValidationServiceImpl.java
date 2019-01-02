package io.choerodon.gitlab.app.service.impl;

import io.choerodon.gitlab.app.service.UrlValidationService;
import org.gitlab4j.api.GitLabApi;
import org.springframework.stereotype.Service;

/**
 * @author zmf
 */
@Service
public class UrlValidationServiceImpl implements UrlValidationService {
    @Override
    public boolean validateUrlAndAccessToken(String cloneUrl, String accessToken) {
        try {
            String hostUrl, namespace, project;
            // http(s) 协议
            String[] fields = cloneUrl.split("//");
            hostUrl = fields[0];
            fields = fields[1].split("/");
            hostUrl += "//" + fields[0];
            namespace = fields[1];
            project = fields[2].split("\\.")[0];

            GitLabApi gitLabApi = new GitLabApi(GitLabApi.ApiVersion.V4, hostUrl, accessToken);
            gitLabApi.getProjectApi().getProject(namespace, project);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
