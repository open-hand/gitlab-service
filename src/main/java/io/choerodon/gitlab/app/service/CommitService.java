package io.choerodon.gitlab.app.service;

import org.gitlab4j.api.models.Commit;

/**
 * Created by zzy on 2018/1/14.
 */
public interface CommitService {
    Commit getCommit(Integer projectId, String sha, String userName);
}
