package io.choerodon.gitlab.app.service;

import java.util.List;

import io.choerodon.gitlab.api.dto.CommitDTO;
import org.gitlab4j.api.models.Commit;

import io.choerodon.gitlab.api.dto.CommitStatuseDTO;

/**
 * Created by zzy on 2018/1/14.
 */
public interface CommitService {
    CommitDTO getCommit(Integer projectId, String sha, Integer userId);

    List<CommitStatuseDTO> getCommitStatuse(Integer projectId, String sha, Integer userId);

    List<Commit> getCommits(Integer gitLabProjectId, String ref, String since);

    List<Commit> listCommits(Integer gilabProjectId , int page, int size, Integer userId);
}
