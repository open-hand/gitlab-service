package io.choerodon.gitlab.api.controller.v1;

import java.util.List;
import java.util.Optional;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.gitlab4j.api.models.Job;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.choerodon.core.exception.FeignException;
import io.choerodon.gitlab.app.service.JobService;
import io.choerodon.gitlab.infra.dto.AppExternalConfigDTO;


/**
 * Created by zzy on 2018/1/9.
 */
@RestController
@RequestMapping(value = "/v1/projects/{projectId}")
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
     * @param userId     用户Id
     * @return List
     */
    @ApiOperation(value = "查询项目下pipeline的jobs")
    @GetMapping(value = "/pipelines/{pipelineId}/jobs")
    public ResponseEntity<List<Job>> list(
            @ApiParam(value = "项目id", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "pipelineId", required = true)
            @PathVariable Integer pipelineId,
            @ApiParam(value = "userId")
            @RequestParam(required = false) Integer userId,
            AppExternalConfigDTO appExternalConfigDTO) {
        return Optional.ofNullable(jobService.listJobs(projectId, pipelineId, userId, appExternalConfigDTO))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new FeignException("error.jobs.get"));
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
                .orElseThrow(() -> new FeignException("error.jobs.get"));
    }
    /**
     * 查询某个Job执行日志
     *
     * @param projectId 项目id
     * @param jobId     job id
     * @return Job
     */
    @ApiOperation(value = "查询某个Job执行日志")
    @GetMapping(value = "/jobs/{jobId}/trace")
    public ResponseEntity<String> queryTrace(
            @ApiParam(value = "项目id", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "jobId", required = true)
            @PathVariable Integer jobId,
            @ApiParam(value = "userId")
            @RequestParam(value = "userId", required = false) Integer userId,
            AppExternalConfigDTO appExternalConfigDTO) {
        return Optional.ofNullable(jobService.queryTrace(projectId, userId, jobId, appExternalConfigDTO))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new FeignException("error.jobs.get.trace"));
    }

    @ApiOperation(value = "重试job")
    @PutMapping(value = "/jobs/{jobId}/retry")
    public ResponseEntity<Job> retry(
            @ApiParam(value = "项目id", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "jobId", required = true)
            @PathVariable Integer jobId,
            @ApiParam(value = "userId")
            @RequestParam(value = "userId", required = false) Integer userId,
            AppExternalConfigDTO appExternalConfigDTO) {
        return Optional.ofNullable(jobService.retry(projectId, userId, jobId, appExternalConfigDTO))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new FeignException("error.jobs.retry"));
    }

    @ApiOperation(value = "执行 manul状态的job")
    @PutMapping(value = "/jobs/{jobId}/play")
    public ResponseEntity<Job> play(
            @ApiParam(value = "项目id", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "jobId", required = true)
            @PathVariable Integer jobId,
            @ApiParam(value = "userId")
            @RequestParam(value = "userId") Integer userId) {
        return ResponseEntity.ok(jobService.play(projectId, userId, jobId));
    }
}
