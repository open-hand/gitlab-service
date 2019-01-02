package io.choerodon.gitlab.app.service;

/**
 * @author zmf
 */
public interface UrlValidationService {
    /**
     * 验证用于克隆仓库的url及授权的access token是否有效
     *
     * @param url         用于克隆仓库的url(http协议, https协议)
     * @param accessToken gitlab授权的access token
     * @return true 如果有效
     */
    boolean validateUrlAndAccessToken(String url, String accessToken);
}
