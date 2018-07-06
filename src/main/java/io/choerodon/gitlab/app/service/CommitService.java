package io.choerodon.gitlab.app.service;

import java.util.Date;
import java.util.List;

import org.gitlab4j.api.models.Commit;
import org.gitlab4j.api.models.CommitStatuse;

/**
 * Created by zzy on 2018/1/14.
 */
public interface CommitService {
    Commit getCommit(Integer projectId, String sha, Integer userId);

    List<CommitStatuse> getCommitStatuse(Integer projectId, String sha, Integer userId);

    List<Commit> getCommits(Integer gitLabProjectId, String ref, Date since);
}
