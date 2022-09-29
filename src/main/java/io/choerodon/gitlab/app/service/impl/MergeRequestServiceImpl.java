package io.choerodon.gitlab.app.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.gitlab4j.api.Constants;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Commit;
import org.gitlab4j.api.models.MergeRequest;
import org.gitlab4j.api.models.MergeRequestParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.choerodon.core.exception.FeignException;
import io.choerodon.gitlab.api.vo.MergeRequestVO;
import io.choerodon.gitlab.app.service.MergeRequestService;
import io.choerodon.gitlab.infra.common.client.Gitlab4jClient;
import io.choerodon.gitlab.infra.common.exception.MergeRequestNotFoundException;


@Service
public class MergeRequestServiceImpl implements MergeRequestService {

    @Value("${gitlab.url}")
    private String url;

    @Value("${gitlab.privateToken}")
    private String privateToken;

    private Gitlab4jClient gitlab4jclient;

    public MergeRequestServiceImpl(Gitlab4jClient gitlab4jclient) {
        this.gitlab4jclient = gitlab4jclient;
    }

    @Override
    public MergeRequest createMergeRequest(Integer projectId, MergeRequestVO mergeRequestVO, Integer userId) {
        try {
            MergeRequestParams mergeRequestParams = new MergeRequestParams();
            mergeRequestParams.withSourceBranch(mergeRequestVO.getSourceBranch());
            mergeRequestParams.withTargetBranch(mergeRequestVO.getTargetBranch());
            mergeRequestParams.withTitle(mergeRequestVO.getTitle());
            mergeRequestParams.withDescription(mergeRequestVO.getDescription());
            mergeRequestParams.withRemoveSourceBranch(mergeRequestVO.getRemoveSourceBranch());
            mergeRequestParams.withApprovalsBeforeMerge(mergeRequestVO.getApprovalsBeforeMerge());
            mergeRequestParams.withAssigneeId(mergeRequestVO.getAssigneeId());
            mergeRequestParams.withAssigneeIds(mergeRequestVO.getAssigneeIds());


            return gitlab4jclient.getGitLabApi(userId).getMergeRequestApi()
                    .createMergeRequest(projectId, mergeRequestParams);
        } catch (GitLabApiException e) {
            throw new FeignException("error.mergeRequest.create");
        }
    }


    @Override
    public MergeRequest queryMergeRequest(Integer projectId, Integer mergeRequestId, Integer userId) {
        try {
            return gitlab4jclient.getGitLabApi(userId).getMergeRequestApi()
                    .getMergeRequest(projectId, mergeRequestId);
        } catch (GitLabApiException e) {
            throw new FeignException("error.mergeRequest.get");
        }
    }

    @Override
    public List<MergeRequest> listMergeRequests(Integer projectId) {
        try {
            return gitlab4jclient.getGitLabApi(null).getMergeRequestApi()
                    .getMergeRequests(projectId, Constants.MergeRequestState.ALL);
        } catch (GitLabApiException e) {
            throw new FeignException("error.mergeRequests.list");
        }
    }

    @Override
    public List<Integer> listMergeRequestIds(Integer projectId) {
        List<MergeRequest> mergeRequestList = listMergeRequests(projectId);
        return mergeRequestList.stream().map(MergeRequest::getIid).collect(Collectors.toList());
    }

    @Override
    public MergeRequest acceptMergeRequest(Integer projectId, Integer mergeRequestId,
                                           String mergeCommitMessage, Boolean shouldRemoveSourceBranch,
                                           Boolean mergeWhenPipelineSucceeds, Integer userId) {
        try {
            return gitlab4jclient.getGitLabApi(userId)
                    .getMergeRequestApi()
                    .acceptMergeRequest(projectId,
                            mergeRequestId,
                            mergeCommitMessage,
                            shouldRemoveSourceBranch,
                            mergeWhenPipelineSucceeds);
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public List<Commit> listCommits(Integer projectId, Integer mergeRequestId, Integer userId) {
        try {
            return gitlab4jclient.getGitLabApi(userId)
                    .getMergeRequestApi().getCommits(projectId, mergeRequestId);
        } catch (GitLabApiException e) {
            if (e.getMessage().equals("404 Not found")) {
                throw new MergeRequestNotFoundException(e.getMessage(), e);
            }
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteMergeRequest(Integer projectId, Integer mergeRequestId) {
        try {
            gitlab4jclient.getGitLabApi()
                    .getMergeRequestApi().deleteMergeRequest(projectId, mergeRequestId);
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e);
        }
    }
}
