package io.choerodon.gitlab.domain.config;

import io.choerodon.core.exception.CommonException;
import io.choerodon.core.exception.FeignException;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class GitlabHealthy implements HealthIndicator {

    @Value("${gitlab.url}")
    private String url;

    @Value("${gitlab.privateToken}")
    private String privateToken;

    @Override
    public Health health() {
        GitLabApi gitLabApi = new GitLabApi(url, privateToken);
        int errorCode = 0;
        try {
            gitLabApi.getUserApi().getCurrentUser();
        } catch (GitLabApiException e) {
            if (e.getHttpStatus() == 401) {
                errorCode = 401;
            } else if (e.getHttpStatus() == 404) {
                errorCode = 404;
            } else {
                throw new CommonException(e);
            }
        }
        if (errorCode == 401 || errorCode == 404) {
            return Health.down().withDetail("Error Code", "the token or the url is error, or the ingress resolution error!").build();
        } else {
            try {
                gitLabApi.getApplicationApi().modifyApplicationSetting(true);
            } catch (GitLabApiException e) {
                throw new FeignException(e);
            }
            return Health.up().build();
        }
    }
}
