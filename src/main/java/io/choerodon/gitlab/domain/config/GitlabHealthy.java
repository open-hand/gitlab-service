package io.choerodon.gitlab.domain.config;

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
        try {
            gitLabApi.getUserApi().getCurrentUser();
            return Health.up().build();
        } catch (GitLabApiException e) {
            if (e.getHttpStatus() == 401 || e.getHttpStatus() == 404) {
                return Health.down().withDetail("Error Code", "the token or the url is error, or the ingress resolution error!").build();
            } else {
                throw new FeignException(e);
            }
        }
    }
}
