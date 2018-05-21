package io.choerodon.gitlab.api.controller.v1;

import java.util.List;
import java.util.Optional;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.gitlab4j.api.models.Pipeline;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.choerodon.core.exception.CommonException;
import io.choerodon.gitlab.api.dto.PipelineDto;
import io.choerodon.gitlab.app.service.PipelineService;

@RestController
@RequestMapping("/v1/projects/{projectId}/pipelines")
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
            @RequestParam Integer size) {
        return Optional.ofNullable(pipelineService.listPipelinesByPage(projectId, page, size))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.pipeline.page"));
    }

    /**
     * 查询项目下的pipelines
     *
     * @param projectId 项目 Id
     * @return List
     */
    @ApiOperation(value = "查询项目下的pipelines")
    @GetMapping
    public ResponseEntity<List<Pipeline>> listPipeline(
            @ApiParam(value = "项目id", required = true)
            @PathVariable Integer projectId) {
        return Optional.ofNullable(pipelineService.listPipelines(projectId))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.pipeline.list"));
    }

    /**
     * 查询某个pipelines的具体信息
     *
     * @param projectId  项目 Id
     * @param pipelineId 流水线 Id
     * @param userName   用户名
     * @return Pipeline
     */
    @ApiOperation(value = "查询某个pipelines的具体信息")
    @GetMapping(value = "/{pipelineId}")
    public ResponseEntity<PipelineDto> query(
            @ApiParam(value = "项目id", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "pipelineId", required = true)
            @PathVariable Integer pipelineId,
            @ApiParam(value = "userName")
            @RequestParam(required = false) String userName) {
        return Optional.ofNullable(pipelineService.queryPipeline(projectId, pipelineId, userName))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.pipeline.query"));
    }

    /**
     * Retry jobs in a pipeline
     *
     * @param projectId  项目id
     * @param pipelineId 流水线id
     * @param userName   用户名
     * @return Pipeline
     */
    @ApiOperation(value = "Retry jobs in a pipeline")
    @GetMapping(value = "/{pipelineId}/retry")
    public ResponseEntity<Pipeline> retry(
            @ApiParam(value = "项目id", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "pipelineId", required = true)
            @PathVariable Integer pipelineId,
            @ApiParam(value = "userName")
            @RequestParam(required = false) String userName) {
        return Optional.ofNullable(pipelineService.retryPipeline(projectId, pipelineId, userName))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.pipeline.retry"));
    }

    /**
     * Cancel a pipelines jobs
     *
     * @param projectId  项目id
     * @param pipelineId 流水线id
     * @param userName   用户名
     * @return Pipeline
     */
    @ApiOperation(value = "Cancel a pipelines jobs ")
    @GetMapping(value = "/{pipelineId}/cancel")
    public ResponseEntity<Pipeline> cancel(
            @ApiParam(value = "项目id", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "pipelineId", required = true)
            @PathVariable Integer pipelineId,
            @ApiParam(value = "userName")
            @RequestParam(required = false) String userName) {
        return Optional.ofNullable(pipelineService.cancelPipeline(projectId, pipelineId, userName))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.pipeline.cancel"));
    }

}
