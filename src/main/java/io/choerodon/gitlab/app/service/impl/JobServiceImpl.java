package io.choerodon.gitlab.app.service.impl;

import java.util.List;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Job;
import org.springframework.stereotype.Service;

import io.choerodon.core.exception.FeignException;
import io.choerodon.gitlab.app.service.JobService;
import io.choerodon.gitlab.infra.common.client.Gitlab4jClient;
import io.choerodon.gitlab.infra.dto.AppExternalConfigDTO;
import io.choerodon.gitlab.infra.util.ExternalGitlabApiUtil;


@Service
public class JobServiceImpl implements JobService {

    private Gitlab4jClient gitlab4jclient;

    public JobServiceImpl(Gitlab4jClient gitlab4jclient) {
        this.gitlab4jclient = gitlab4jclient;
    }

    @Override
    public List<Job> listJobs(Integer projectId, Integer pipelineId, Integer userId, AppExternalConfigDTO appExternalConfigDTO) {
        GitLabApi gitLabApi;
        if (appExternalConfigDTO == null || appExternalConfigDTO.getGitlabUrl() == null) {
            gitLabApi = gitlab4jclient.getGitLabApi(userId);
        } else {
            gitLabApi = ExternalGitlabApiUtil.createGitLabApi(appExternalConfigDTO);
        }
        try {
            return gitLabApi.getJobApi().getJobsForPipeline(projectId, pipelineId);
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public Job queryJob(Integer projectId, Integer jobId) {
        try {
            return gitlab4jclient.getGitLabApi(null)
                    .getJobApi().getJob(projectId, jobId);
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public String queryTrace(Integer projectId, Integer userId, Integer jobId, AppExternalConfigDTO appExternalConfigDTO) {
        try {
            GitLabApi gitLabApi;
            if (appExternalConfigDTO == null || appExternalConfigDTO.getGitlabUrl() == null) {
                gitLabApi = gitlab4jclient.getGitLabApi(userId);
            } else {
                gitLabApi = ExternalGitlabApiUtil.createGitLabApi(appExternalConfigDTO);
            }
            return gitLabApi
                    .getJobApi().getTrace(projectId, jobId);
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public Job retry(Integer projectId, Integer userId, Integer jobId, AppExternalConfigDTO appExternalConfigDTO) {
        try {
            GitLabApi gitLabApi;
            if (appExternalConfigDTO == null || appExternalConfigDTO.getGitlabUrl() == null) {
                gitLabApi = gitlab4jclient.getGitLabApi(userId);
            } else {
                gitLabApi = ExternalGitlabApiUtil.createGitLabApi(appExternalConfigDTO);
            }
            return gitLabApi
                    .getJobApi().retryJob(projectId, jobId);
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e);
        }
    }
}
