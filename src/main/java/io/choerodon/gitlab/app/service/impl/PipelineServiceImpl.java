package io.choerodon.gitlab.app.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Pipeline;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import io.choerodon.core.exception.CommonException;
import io.choerodon.gitlab.api.dto.PipelineDto;
import io.choerodon.gitlab.app.service.PipelineService;
import io.choerodon.gitlab.infra.common.client.Gitlab4jClient;


@Service
public class PipelineServiceImpl implements PipelineService {

    private Gitlab4jClient gitlab4jclient;

    public PipelineServiceImpl(Gitlab4jClient gitlab4jclient) {
        this.gitlab4jclient = gitlab4jclient;
    }

    @Override
    public List<Pipeline> listPipelinesByPage(Integer projectId, Integer page, Integer size) {
        GitLabApi gitLabApi = gitlab4jclient.getGitLabApi(null);
        try {
            return gitLabApi.getPipelineApi().getPipelines(projectId, page, size == 0 ? 40 : size);
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage());
        }
    }

    @Override
    public List<Pipeline> listPipelines(Integer projectId) {
        GitLabApi gitLabApi = gitlab4jclient.getGitLabApi(null);
        try {
            return gitLabApi.getPipelineApi().getPipelines(projectId);
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage());
        }
    }

    @Override
    public PipelineDto queryPipeline(Integer projectId, Integer pipelineId, String userName) {
        try {
            Pipeline pipeline = gitlab4jclient.getGitLabApi(userName)
                    .getPipelineApi().getPipeline(projectId, pipelineId);
            PipelineDto pipelineDto = new PipelineDto();
            BeanUtils.copyProperties(pipeline, pipelineDto);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = formatter.format(pipeline.getCreatedAt());
            pipelineDto.setCreatedAt(dateString);
            return pipelineDto;
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage());
        }
    }

    @Override
    public Pipeline retryPipeline(Integer projectId, Integer pipelineId, String userName) {
        try {
            return gitlab4jclient.getGitLabApi(userName)
                    .getPipelineApi().retryPipelineJob(projectId, pipelineId);
        } catch (GitLabApiException e) {
            return new Pipeline();
        }
    }

    @Override
    public Pipeline cancelPipeline(Integer projectId, Integer pipelineId, String userName) {
        try {
            return gitlab4jclient.getGitLabApi(userName)
                    .getPipelineApi().cancelPipelineJobs(projectId, pipelineId);
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage());
        }
    }
}
