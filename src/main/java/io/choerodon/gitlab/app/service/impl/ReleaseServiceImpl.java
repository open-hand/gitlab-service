package io.choerodon.gitlab.app.service.impl;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Release;
import org.gitlab4j.api.models.ReleaseParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.choerodon.core.exception.CommonException;
import io.choerodon.gitlab.app.service.ReleaseService;
import io.choerodon.gitlab.infra.common.client.Gitlab4jClient;

/**
 * 〈功能简述〉
 * 〈〉
 *
 * @author wanghao
 * @since 2022/3/31 16:04
 */
@Service
public class ReleaseServiceImpl implements ReleaseService {
    @Autowired
    private Gitlab4jClient gitlab4jclient;

    @Override
    public Release create(Integer projectId, Integer userId, ReleaseParams releaseParams) {
        GitLabApi gitLabApi = gitlab4jclient.getGitLabApi(userId);
        try {

            return gitLabApi.getReleasesApi().createRelease(projectId, releaseParams);
        } catch (GitLabApiException e) {

            throw new CommonException(e);
        }
    }
}
