package io.choerodon.gitlab.app.service;

import java.util.List;

import org.gitlab4j.api.Constants;
import org.gitlab4j.api.models.Commit;
import org.gitlab4j.api.models.MergeRequest;

import io.choerodon.gitlab.api.vo.MergeRequestVO;


public interface MergeRequestService {

    /**
     * 创建merge请求
     *
     * @param projectId 工程ID
     * @param userId    用户Id Optional
     * @return MergeRequest
     */
    MergeRequest createMergeRequest(Integer projectId, MergeRequestVO mergeRequestParams, Integer userId);


    /**
     * 获取合并请求merge request
     *
     * @param projectId      项目id
     * @param mergeRequestId 合并请求id
     * @param userId         用户Id Optional
     * @return MergeRequest
     */
    MergeRequest queryMergeRequest(Integer projectId, Integer mergeRequestId, Integer userId);

    /**
     * 获取合并请求列表merge request
     *
     * @param projectId 项目id
     * @return MergeRequest
     */
    List<MergeRequest> listMergeRequests(Integer projectId, Constants.MergeRequestState mergeRequestState);

    /**
     * 获取合并请求列表merge request
     *
     * @param projectId 项目id
     * @return MergeRequest
     */
    List<Integer> listMergeRequestIds(Integer projectId);


    /**
     * 执行merge请求
     *
     * @param projectId                 项目id
     * @param mergeRequestId            merge请求的id
     * @param mergeCommitMessage        merge的commit信息
     * @param shouldRemoveSourceBranch  merge后是否删除该分支
     * @param mergeWhenPipelineSucceeds pipeline成功后自动合并分支
     * @param userId                    用户Id
     * @return MergeRequest
     */
    MergeRequest acceptMergeRequest(
            Integer projectId, Integer mergeRequestId, String mergeCommitMessage,
            Boolean shouldRemoveSourceBranch, Boolean mergeWhenPipelineSucceeds,
            Integer userId);

    /**
     * 查询合并请求的commits
     *
     * @param projectId      项目id
     * @param mergeRequestId 合并请求ID
     * @param userId         用户Id
     * @return List
     */
    List<Commit> listCommits(Integer projectId, Integer mergeRequestId, Integer userId);

    /**
     * 删除合并请求
     *
     * @param projectId      项目id
     * @param mergeRequestId 合并请求ID
     */
    void deleteMergeRequest(Integer projectId, Integer mergeRequestId);

}

