package io.choerodon.gitlab.app.service.impl;

import io.choerodon.core.exception.FeignException;
import io.choerodon.gitlab.api.vo.PipelineVO;
import io.choerodon.gitlab.app.service.PipelineService;
import io.choerodon.gitlab.infra.common.client.Gitlab4jClient;
import io.choerodon.gitlab.infra.dto.AppExternalConfigDTO;
import io.choerodon.gitlab.infra.util.ExternalGitlabApiUtil;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Pipeline;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;


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
    public PipelineVO queryPipeline(Integer projectId, Integer pipelineId, Integer userId, AppExternalConfigDTO appExternalConfigDTO) {
        try {
            GitLabApi gitLabApi;
            if (appExternalConfigDTO == null || appExternalConfigDTO.getGitlabUrl() == null) {
                gitLabApi = gitlab4jclient.getGitLabApi(userId);
            } else {
                gitLabApi = ExternalGitlabApiUtil.createGitLabApi(appExternalConfigDTO);
            }
            Pipeline pipeline = gitLabApi
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
    public Pipeline retryPipeline(Integer projectId, Integer pipelineId, Integer userId, AppExternalConfigDTO appExternalConfigDTO) {
        GitLabApi gitLabApi;
        if (appExternalConfigDTO == null || appExternalConfigDTO.getGitlabUrl() == null) {
            gitLabApi = gitlab4jclient.getGitLabApi(userId);
        } else {
            gitLabApi = ExternalGitlabApiUtil.createGitLabApi(appExternalConfigDTO);
        }
        try {
            return gitLabApi
                    .getPipelineApi().retryPipelineJob(projectId, pipelineId);
        } catch (GitLabApiException e) {
            return new Pipeline();
        }
    }

    @Override
    public Pipeline cancelPipeline(Integer projectId, Integer pipelineId, Integer userId, AppExternalConfigDTO appExternalConfigDTO) {
        GitLabApi gitLabApi;
        if (appExternalConfigDTO == null || appExternalConfigDTO.getGitlabUrl() == null) {
            gitLabApi = gitlab4jclient.getGitLabApi(userId);
        } else {
            gitLabApi = ExternalGitlabApiUtil.createGitLabApi(appExternalConfigDTO);
        }
        try {
            return gitLabApi
                    .getPipelineApi().cancelPipelineJobs(projectId, pipelineId);
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public Pipeline createPipeline(Integer projectId, Integer userId, String ref, AppExternalConfigDTO appExternalConfigDTO) {
        GitLabApi gitLabApi;
        if (appExternalConfigDTO == null || appExternalConfigDTO.getGitlabUrl() == null) {
            gitLabApi = gitlab4jclient.getGitLabApi(userId);
        } else {
            gitLabApi = ExternalGitlabApiUtil.createGitLabApi(appExternalConfigDTO);
        }
        try {
            return gitLabApi
                    .getPipelineApi().createPipeline(projectId, ref);
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e.getHttpStatus());
        }
    }
}
