package io.choerodon.gitlab.app.service;

import java.util.List;

import org.gitlab4j.api.models.Commit;
import org.gitlab4j.api.models.CommitStatuse;

import io.choerodon.gitlab.api.dto.CommitStatuseDTO;

/**
 * Created by zzy on 2018/1/14.
 */
public interface CommitService {
    Commit getCommit(Integer projectId, String sha, Integer userId);

    List<CommitStatuseDTO> getCommitStatuse(Integer projectId, String sha, Integer userId);

    List<Commit> getCommits(Integer gitLabProjectId, String ref, String since);
}
