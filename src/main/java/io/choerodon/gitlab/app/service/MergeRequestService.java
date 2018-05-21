package io.choerodon.gitlab.app.service;

import java.util.List;

import org.gitlab4j.api.models.Commit;
import org.gitlab4j.api.models.MergeRequest;


public interface MergeRequestService {

    /**
     * 创建merge请求
     *
     * @param projectId    工程ID
     * @param sourceBranch 源分支
     * @param targetBranch 目标分支
     * @param title        标题
     * @param description  描述
     * @param username     用户名 Optional
     * @return MergeRequest
     */
    MergeRequest createMergeRequest(Integer projectId, String sourceBranch,
                                    String targetBranch, String title,
                                    String description, String username);

    /**
     * 刷新合并请求merge_status
     *
     * @param projectId      项目id
     * @param mergeRequestId 合并请求id
     * @param username       用户名 Optional
     */
    void updateMergeRequest(Integer projectId, Integer mergeRequestId, String username);


    /**
     * 获取合并请求merge request
     *
     * @param projectId      项目id
     * @param mergeRequestId 合并请求id
     * @param username       用户名 Optional
     * @return MergeRequest
     */
    MergeRequest queryMergeRequest(Integer projectId, Integer mergeRequestId, String username);

    /**
     * 获取合并请求列表merge request
     *
     * @param projectId 项目id
     * @return MergeRequest
     */
    List<MergeRequest> listMergeRequests(Integer projectId);


    /**
     * 执行merge请求
     *
     * @param projectId                 项目id
     * @param mergeRequestId            merge请求的id
     * @param mergeCommitMessage        merge的commit信息
     * @param shouldRemoveSourceBranch  merge后是否删除该分支
     * @param mergeWhenPipelineSucceeds pipeline成功后自动合并分支
     * @param username                  用户名
     * @return MergeRequest
     */
    MergeRequest acceptMergeRequest(
            Integer projectId, Integer mergeRequestId, String mergeCommitMessage,
            Boolean shouldRemoveSourceBranch, Boolean mergeWhenPipelineSucceeds,
            String username);

    /**
     * 查询合并请求的commits
     *
     * @param projectId      项目id
     * @param mergeRequestId 合并请求ID
     * @return List
     */
    List<Commit> listCommits(Integer projectId, Integer mergeRequestId);

    /**
     * 删除合并请求
     *
     * @param projectId      项目id
     * @param mergeRequestId 合并请求ID
     */
    void deleteMergeRequest(Integer projectId, Integer mergeRequestId);

}

