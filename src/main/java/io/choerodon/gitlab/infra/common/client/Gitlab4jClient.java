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
    private static final String ADMIN_USERNAME = "admin";

    private static final String ADMIN_REAL_USERNAME = "admin1";

    private static final String ROOT_USERNAME = "root";

    @Value("${gitlab.url}")
    private String url;

    @Value("${gitlab.privateToken}")
    private String privateToken;

    private ConcurrentHashMap<String, GitLabApi> clientMap = new ConcurrentHashMap<>();

    private GitLabApi createGitLabApi(GitLabApi.ApiVersion apiVersion, String username) {
        try {
            GitLabApi newGitLabApi = new GitLabApi(apiVersion, url, privateToken);
            if (username != null) {
                newGitLabApi.sudo(username);
            }
            return newGitLabApi;
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage());
        }
    }

    public GitLabApi getGitLabApiUser(String username) {
        return getGitLabApi(GitLabApi.ApiVersion.V4, username);
    }

    public GitLabApi getGitLabApiVersion(GitLabApi.ApiVersion apiVersion) {
        return getGitLabApi(apiVersion, ROOT_USERNAME);
    }

    public GitLabApi getGitLabApi() {
        return getGitLabApiUser(ROOT_USERNAME);
    }

    public GitLabApi getGitLabApi(String username) {
        return getGitLabApi(GitLabApi.ApiVersion.V4, getRealUsername(username));
    }

    /**
     * 创建gitLabApi
     *
     * @param apiVersion api版本
     * @param username   用户名
     * @return GitLabApi
     */
    public GitLabApi getGitLabApi(GitLabApi.ApiVersion apiVersion, String username) {
        String key = apiVersion.getApiNamespace() + "-" + username;
        GitLabApi gitLabApi = clientMap.get(key);
        if (gitLabApi != null) {
            return gitLabApi;
        } else {
            gitLabApi = createGitLabApi(apiVersion, username);
            clientMap.put(key, gitLabApi);
            return gitLabApi;
        }
    }

    /**
     * 获取用户上下文用户名 或 指定用户名
     * 区分 admin
     *
     * @param originUsername 用户名
     * @return String
     */
    public String getRealUsername(String originUsername) {
        CustomUserDetails details = DetailsHelper.getUserDetails();
        String username = originUsername == null ? details.getUsername() : originUsername;
        if (ADMIN_USERNAME.equals(username)) {
            return ADMIN_REAL_USERNAME;
        }
        return username;
    }
}
