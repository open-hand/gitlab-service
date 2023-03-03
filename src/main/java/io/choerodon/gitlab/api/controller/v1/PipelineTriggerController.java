package io.choerodon.gitlab.api.controller.v1;

import java.util.List;
import java.util.Optional;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.gitlab4j.api.models.PipelineTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.choerodon.core.exception.FeignException;
import io.choerodon.gitlab.app.service.PipelineTriggerService;

@RestController
@RequestMapping(value = "/v1/projects/{projectId}/pipelines_triggers")
public class PipelineTriggerController {


    @Autowired
    private PipelineTriggerService pipelineTriggerService;

    /**
     * 创建流水线自动触发trigger
     *
     * @param projectId 项目id
     * @param userId    用户id
     * @return Pipeline
     */
    @ApiOperation(value = "Create a pipelines")
    @PostMapping
    public ResponseEntity<PipelineTrigger> create(
            @ApiParam(value = "项目id", required = true)
            @PathVariable(value = "projectId") Integer projectId,
            @ApiParam(value = "userId")
            @RequestParam(value = "userId") Integer userId,
            @ApiParam(value = "trigger name")
            @RequestParam String description) {
        return Optional.ofNullable(pipelineTriggerService.createPipelineTrigger(projectId, userId, description))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new FeignException("error.pipeline.create"));
    }

    /**
     * 删除流水线自动触发trigger
     *
     * @param projectId 项目id
     * @param userId    用户id
     * @return Pipeline
     */
    @ApiOperation(value = "Create a pipelines")
    @DeleteMapping
    public ResponseEntity<Void> delete(
            @ApiParam(value = "项目id", required = true)
            @PathVariable(value = "projectId") Integer projectId,
            @ApiParam(value = "userId")
            @RequestParam(value = "userId") Integer userId,
            @ApiParam(value = "trigger id")
            @RequestParam Integer triggerId) {
        pipelineTriggerService.deletePipelineTrigger(projectId, userId, triggerId);
        return ResponseEntity.ok().build();
    }

    /**
     * 查询流水线自动触发trigger
     *
     * @param projectId 项目id
     * @param userId    用户id
     * @return Pipeline
     */
    @ApiOperation(value = "Create a pipelines")
    @GetMapping
    public ResponseEntity<List<PipelineTrigger>> listPipelineTrigger(
            @ApiParam(value = "项目id", required = true)
            @PathVariable(value = "projectId") Integer projectId,
            @ApiParam(value = "userId")
            @RequestParam(value = "userId") Integer userId) {
        return Optional.ofNullable(pipelineTriggerService.listPipelineTrigger(projectId, userId))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new FeignException("error.pipeline.list"));
    }
}
