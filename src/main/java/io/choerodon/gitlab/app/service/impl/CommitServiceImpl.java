package io.choerodon.gitlab.app.service.impl;

import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Commit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.choerodon.core.exception.CommonException;
import io.choerodon.gitlab.app.service.CommitService;
import io.choerodon.gitlab.infra.common.client.Gitlab4jClient;

/**
 * Created by zzy on 2018/1/14.
 */
@Service
public class CommitServiceImpl implements CommitService {

    @Autowired
    private Gitlab4jClient gitlab4jclient;

    @Override
    public Commit getCommit(Integer projectId, String sha, String userName) {

        try {
            return gitlab4jclient.getGitLabApi(userName).getCommitsApi().getCommit(projectId, sha);
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage());
        }
    }
}
