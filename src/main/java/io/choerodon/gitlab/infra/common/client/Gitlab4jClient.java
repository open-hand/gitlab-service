package io.choerodon.gitlab.infra.common.client;

import java.util.concurrent.ConcurrentHashMap;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.choerodon.core.exception.CommonException;
import io.choerodon.core.oauth.CustomUserDetails;
import io.choerodon.core.oauth.DetailsHelper;


/**
 * Created by zzy on 2018/3/20.
 */
@Component
public class Gitlab4jClient {
    private static final Integer ROOT_USER_ID = 1;

    @Value("${gitlab.url}")
    private String url;

    @Value("${gitlab.privateToken}")
    private String privateToken;

    private ConcurrentHashMap<String, GitLabApi> clientMap = new ConcurrentHashMap<>();

    private GitLabApi createGitLabApi(GitLabApi.ApiVersion apiVersion, Integer userId) {
        try {
            GitLabApi newGitLabApi = new GitLabApi(apiVersion, url, privateToken);
            if (userId != null) {
                newGitLabApi.setSudoAsId(userId);
            }
            return newGitLabApi;
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage());
        }
    }

    public GitLabApi getGitLabApiUser(Integer userId) {
        return getGitLabApi(GitLabApi.ApiVersion.V4, userId);
    }

    public GitLabApi getGitLabApiVersion(GitLabApi.ApiVersion apiVersion) {
        return getGitLabApi(apiVersion, ROOT_USER_ID);
    }

    public GitLabApi getGitLabApi() {
        return getGitLabApiUser(ROOT_USER_ID);
    }

    public GitLabApi getGitLabApi(Integer userId) {
        return getGitLabApi(GitLabApi.ApiVersion.V4, userId);
    }

    /**
     * 创建gitLabApi
     *
     * @param apiVersion api版本
     * @param userId     用户ID
     * @return GitLabApi
     */
    public GitLabApi getGitLabApi(GitLabApi.ApiVersion apiVersion, Integer userId) {
        String key = apiVersion.getApiNamespace() + "-" + userId;
        GitLabApi gitLabApi = clientMap.get(key);
        if (gitLabApi != null) {
            return gitLabApi;
        } else {
            gitLabApi = createGitLabApi(apiVersion, userId);
            clientMap.put(key, gitLabApi);
            return gitLabApi;
        }
    }
}
