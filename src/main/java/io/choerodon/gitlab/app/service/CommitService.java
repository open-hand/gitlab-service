package io.choerodon.gitlab.app.service;

import java.util.List;

import org.gitlab4j.api.models.Commit;
import org.gitlab4j.api.models.CommitPayload;

import io.choerodon.gitlab.api.vo.CommitStatuseVO;
import io.choerodon.gitlab.infra.dto.AppExternalConfigDTO;

/**
 * Created by zzy on 2018/1/14.
 */
public interface CommitService {
    Commit getCommit(Integer projectId, String sha, Integer userId);

    List<CommitStatuseVO> getCommitStatuse(Integer projectId, String sha, Integer userId);

    List<Commit> getCommits(Integer gitLabProjectId, String ref, String since);

    List<Commit> listCommits(Integer gilabProjectId, int page, int size, Integer userId);

    List<Commit> getCommitsByRef(Integer gitLabProjectId, String ref, String path);
    /**
     * 创建commit，可以批量操作文件
     *
     * @param projectId     gitlab项目id
     * @param userId        gitlab用户id
     * @param commitPayload 操作文件相关的信息
     */
    void createCommit(Integer projectId, Integer userId, CommitPayload commitPayload);

    List<Commit> listExternalCommits(Integer projectId, Integer page, Integer size, AppExternalConfigDTO appExternalConfigDTO);
}
