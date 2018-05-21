package io.choerodon.gitlab.app.service.impl;

import java.util.List;

import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Job;
import org.springframework.stereotype.Service;

import io.choerodon.core.exception.CommonException;
import io.choerodon.gitlab.app.service.JobService;
import io.choerodon.gitlab.infra.common.client.Gitlab4jClient;


@Service
public class JobServiceImpl implements JobService {

    private Gitlab4jClient gitlab4jclient;

    public JobServiceImpl(Gitlab4jClient gitlab4jclient) {
        this.gitlab4jclient = gitlab4jclient;
    }

    @Override
    public List<Job> listJobs(Integer projectId, Integer pipelineId, String userName) {
        try {
            return gitlab4jclient.getGitLabApi(userName)
                    .getJobApi().getJobsForPipeline(projectId, pipelineId);
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage());
        }
    }

    @Override
    public Job queryJob(Integer projectId, Integer jobId) {
        try {
            return gitlab4jclient.getGitLabApi(null)
                    .getJobApi().getJob(projectId, jobId);
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage());
        }
    }
}
