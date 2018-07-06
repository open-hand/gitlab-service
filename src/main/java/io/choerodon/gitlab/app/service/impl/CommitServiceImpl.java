package io.choerodon.gitlab.app.service.impl;

import java.util.Date;
import java.util.List;

import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Commit;
import org.gitlab4j.api.models.CommitStatuse;
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
    public Commit getCommit(Integer projectId, String sha, Integer userId) {
        try {
            return gitlab4jclient.getGitLabApi(userId).getCommitsApi().getCommit(projectId, sha);
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage());
        }
    }

    @Override
    public List<CommitStatuse> getCommitStatuse(Integer projectId, String sha, Integer userId) {
        try {
            return gitlab4jclient.getGitLabApi(userId).getCommitsApi().getCommitStatus(projectId, sha);
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage());
        }
    }

    @Override
    public List<Commit> getCommits(Integer gitLabProjectId, String ref, Date since) {
        try {
            return gitlab4jclient.getGitLabApi()
                    .getCommitsApi().getCommits(gitLabProjectId, ref, since, new Date(), null);
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage());
        }
    }
}
