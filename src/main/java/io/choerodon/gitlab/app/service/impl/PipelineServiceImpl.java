package io.choerodon.gitlab.app.service.impl;

import java.util.List;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Pipeline;
import org.springframework.stereotype.Service;

import io.choerodon.core.exception.CommonException;
import io.choerodon.gitlab.app.service.PipelineService;
import io.choerodon.gitlab.infra.common.client.Gitlab4jClient;


@Service
public class PipelineServiceImpl implements PipelineService {

    private Gitlab4jClient gitlab4jclient;

    public PipelineServiceImpl(Gitlab4jClient gitlab4jclient) {
        this.gitlab4jclient = gitlab4jclient;
    }

    @Override
    public List<Pipeline> listPipelinesByPage(Integer projectId, Integer page, Integer size, Integer userId) {
        GitLabApi gitLabApi = gitlab4jclient.getGitLabApi(userId);
        try {
            return gitLabApi.getPipelineApi().getPipelines(projectId, page, size == 0 ? 40 : size);
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage());
        }
    }

    @Override
    public List<Pipeline> listPipelines(Integer projectId, Integer userId) {
        GitLabApi gitLabApi = gitlab4jclient.getGitLabApi(userId);
        try {
            return gitLabApi.getPipelineApi().getPipelines(projectId);
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage());

        }
    }

    @Override
    public Pipeline queryPipeline(Integer projectId, Integer pipelineId, Integer userId) {
        try {
            Pipeline pipeline = gitlab4jclient.getGitLabApi(userId)
                    .getPipelineApi().getPipeline(projectId, pipelineId);
            return pipeline;
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage());
        }
    }

    @Override
    public Pipeline retryPipeline(Integer projectId, Integer pipelineId, Integer userId) {
        try {
            return gitlab4jclient.getGitLabApi(userId)
                    .getPipelineApi().retryPipelineJob(projectId, pipelineId);
        } catch (GitLabApiException e) {
            return new Pipeline();
        }
    }

    @Override
    public Pipeline cancelPipeline(Integer projectId, Integer pipelineId, Integer userId) {
        try {
            return gitlab4jclient.getGitLabApi(userId)
                    .getPipelineApi().cancelPipelineJobs(projectId, pipelineId);
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage());
        }
    }
}
