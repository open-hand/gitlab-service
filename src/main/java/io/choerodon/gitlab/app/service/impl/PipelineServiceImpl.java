package io.choerodon.gitlab.app.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Pipeline;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import io.choerodon.core.exception.FeignException;
import io.choerodon.gitlab.api.vo.PipelineVO;
import io.choerodon.gitlab.app.service.PipelineService;
import io.choerodon.gitlab.infra.common.client.Gitlab4jClient;


@Service
public class PipelineServiceImpl implements PipelineService {

    private Gitlab4jClient gitlab4jclient;
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    public PipelineServiceImpl(Gitlab4jClient gitlab4jclient) {
        this.gitlab4jclient = gitlab4jclient;
    }

    @Override
    public List<Pipeline> listPipelinesByPage(Integer projectId, Integer page, Integer size, Integer userId) {
        GitLabApi gitLabApi = gitlab4jclient.getGitLabApi(userId);
        try {
            return gitLabApi.getPipelineApi().getPipelines(projectId, page, size == 0 ? 40 : size);
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage());
        }
    }

    @Override
    public List<Pipeline> listPipelines(Integer projectId, Integer userId) {
        GitLabApi gitLabApi = gitlab4jclient.getGitLabApi(userId);
        try {
            return gitLabApi.getPipelineApi().getPipelines(projectId);
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e);

        }
    }

    @Override
    public PipelineVO queryPipeline(Integer projectId, Integer pipelineId, Integer userId) {
        try {
            Pipeline pipeline = gitlab4jclient.getGitLabApi(userId)
                    .getPipelineApi().getPipeline(projectId, pipelineId);
            PipelineVO pipelineVO = new PipelineVO();
            BeanUtils.copyProperties(pipeline, pipelineVO);
            pipelineVO.setCreated_at(formatter.format(pipeline.getCreatedAt()));
            return pipelineVO;
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e);
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
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public Pipeline createPipeline(Integer projectId, Integer userId, String ref, Map<String, String> variables) {
        try {
            return gitlab4jclient.getGitLabApi(userId)
                    .getPipelineApi().createPipeline(projectId, ref, variables);
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e.getHttpStatus());
        }
    }
}
