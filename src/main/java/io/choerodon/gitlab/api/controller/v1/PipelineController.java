package io.choerodon.gitlab.api.controller.v1;

import java.util.List;
import java.util.Optional;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.gitlab4j.api.models.Pipeline;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.choerodon.core.exception.FeignException;
import io.choerodon.gitlab.api.dto.PipelineDto;
import io.choerodon.gitlab.app.service.PipelineService;

@RestController
@RequestMapping(value = "/v1/projects/{projectId}/pipelines")
public class PipelineController {

    private PipelineService pipelineService;

    public PipelineController(PipelineService pipelineService) {
        this.pipelineService = pipelineService;
    }

    /**
     * 分页查询项目下的pipelines
     *
     * @param projectId 项目 Id
     * @param page      页码
     * @param size      每页大小
     * @param userId    用户Id
     * @return List
     */
    @ApiOperation(value = "查询项目下的pipelines")
    @GetMapping(value = "/page")
    public ResponseEntity<List<Pipeline>> listPipelinesByPage(
            @ApiParam(value = "项目id", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "page", required = true)
            @RequestParam Integer page,
            @ApiParam(value = "size", required = true)
            @RequestParam Integer size,
            @ApiParam(value = "用户Id")
            @RequestParam(required = false) Integer userId
    ) {
        return Optional.ofNullable(pipelineService.listPipelinesByPage(projectId, page, size, userId))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new FeignException("error.pipeline.page"));
    }

    /**
     * 查询项目下的pipelines
     *
     * @param projectId 项目 Id
     * @param userId    用户Id
     * @return List
     */
    @ApiOperation(value = "查询项目下的pipelines")
    @GetMapping
    public ResponseEntity<List<Pipeline>> listPipeline(
            @ApiParam(value = "项目id", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "用户Id")
            @RequestParam(required = false) Integer userId) {
        return Optional.ofNullable(pipelineService.listPipelines(projectId, userId))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new FeignException("error.pipeline.list"));
    }

    /**
     * 查询某个pipelines的具体信息
     *
     * @param projectId  项目 Id
     * @param pipelineId 流水线 Id
     * @param userId     用户Id
     * @return Pipeline
     */
    @ApiOperation(value = "查询某个pipelines的具体信息")
    @GetMapping(value = "/{pipelineId}")
    public ResponseEntity<PipelineDto> query(
            @ApiParam(value = "项目id", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "pipelineId", required = true)
            @PathVariable Integer pipelineId,
            @ApiParam(value = "userId")
            @RequestParam(required = false) Integer userId) {
        return Optional.ofNullable(pipelineService.queryPipeline(projectId, pipelineId, userId))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new FeignException("error.pipeline.query"));
    }

    /**
     * Retry jobs in a pipeline
     *
     * @param projectId  项目id
     * @param pipelineId 流水线id
     * @param userId     用户Id
     * @return Pipeline
     */
    @ApiOperation(value = "Retry jobs in a pipeline")
    @GetMapping(value = "/{pipelineId}/retry")
    public ResponseEntity<Pipeline> retry(
            @ApiParam(value = "项目id", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "pipelineId", required = true)
            @PathVariable Integer pipelineId,
            @ApiParam(value = "userId")
            @RequestParam(required = false) Integer userId) {
        return Optional.ofNullable(pipelineService.retryPipeline(projectId, pipelineId, userId))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new FeignException("error.pipeline.retry"));
    }

    /**
     * Cancel a pipelines jobs
     *
     * @param projectId  项目id
     * @param pipelineId 流水线id
     * @param userId     用户Id
     * @return Pipeline
     */
    @ApiOperation(value = "Cancel a pipelines jobs ")
    @GetMapping(value = "/{pipelineId}/cancel")
    public ResponseEntity<Pipeline> cancel(
            @ApiParam(value = "项目id", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "pipelineId", required = true)
            @PathVariable Integer pipelineId,
            @ApiParam(value = "userId")
            @RequestParam(required = false) Integer userId) {
        return Optional.ofNullable(pipelineService.cancelPipeline(projectId, pipelineId, userId))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new FeignException("error.pipeline.cancel"));
    }

}
