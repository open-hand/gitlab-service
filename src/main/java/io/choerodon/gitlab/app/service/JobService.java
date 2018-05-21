package io.choerodon.gitlab.app.service;

import java.util.List;

import org.gitlab4j.api.models.Job;


public interface JobService {

    /**
     * 查询项目下pipeline的jobs
     *
     * @param projectId  项目id
     * @param pipelineId 流水线id
     * @param userName   用户名
     * @return List
     */
    List<Job> listJobs(Integer projectId, Integer pipelineId, String userName);

    /**
     * 查询项目下某个Job的具体信息
     *
     * @param projectId 项目id
     * @param jobId     job id
     * @return Job
     */
    Job queryJob(Integer projectId, Integer jobId);
}
