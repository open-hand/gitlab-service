package io.choerodon.gitlab.api.controller.v1;

import java.util.List;
import java.util.Optional;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.gitlab4j.api.models.Job;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.choerodon.core.exception.CommonException;
import io.choerodon.gitlab.app.service.JobService;


/**
 * Created by zzy on 2018/1/9.
 */
@RestController
@RequestMapping("/v1/projects/{projectId}")
public class JobController {

    private JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    /**
     * 查询项目下pipeline的jobs
     *
     * @param projectId  项目id
     * @param pipelineId 流水线id
     * @param userName   用户名
     * @return List
     */
    @ApiOperation(value = "查询项目下pipeline的jobs")
    @GetMapping(value = "/pipelines/{pipelineId}/jobs")
    public ResponseEntity<List<Job>> list(
            @ApiParam(value = "项目id", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "pipelineId", required = true)
            @PathVariable Integer pipelineId,
            @ApiParam(value = "userName")
            @RequestParam(required = false) String userName) {
        return Optional.ofNullable(jobService.listJobs(projectId, pipelineId, userName))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.jobs.get"));
    }

    /**
     * 查询项目下某个Job的具体信息
     *
     * @param projectId 项目id
     * @param jobId     job id
     * @return Job
     */
    @ApiOperation(value = "查询项目下某个Job的具体信息")
    @GetMapping(value = "/jobs/{jobId}")
    public ResponseEntity<Job> query(
            @ApiParam(value = "项目id", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "jobId", required = true)
            @PathVariable Integer jobId) {
        return Optional.ofNullable(jobService.queryJob(projectId, jobId))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.jobs.get"));
    }
}
