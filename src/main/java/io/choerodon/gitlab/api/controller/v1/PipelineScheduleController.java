package io.choerodon.gitlab.api.controller.v1;

import java.util.List;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.gitlab4j.api.models.PipelineSchedule;
import org.gitlab4j.api.models.Variable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.choerodon.gitlab.app.service.PipelineScheduleService;
import io.choerodon.gitlab.infra.dto.AppExternalConfigDTO;

@RestController
@RequestMapping(value = "/v1/projects/{project_id}/pipeline_schedules")
public class PipelineScheduleController {

    private PipelineScheduleService pipelineScheduleService;

    @ApiOperation(value = "创建定时执行计划")
    @PostMapping
    public ResponseEntity<PipelineSchedule> create(
            @PathVariable("project_id") Integer projectId,
            @RequestParam(name = "user_id",required = false) Integer userId,
            AppExternalConfigDTO appExternalConfigDTO,
            @RequestBody PipelineSchedule pipelineSchedule) {
        return ResponseEntity.ok(pipelineScheduleService.create(projectId, userId,appExternalConfigDTO, pipelineSchedule));
    }

    @ApiOperation(value = "查询所有定时执行计划")
    @GetMapping()
    public ResponseEntity<List<PipelineSchedule>> list(
            @PathVariable("project_id") Integer projectId,
            @RequestParam(name = "user_id",required = false) Integer userId,
            AppExternalConfigDTO appExternalConfigDTO) {
        return ResponseEntity.ok(pipelineScheduleService.list(projectId, userId, appExternalConfigDTO));
    }

    @ApiOperation(value = "查询定时执行计划")
    @GetMapping("{pipeline_schedule_id}")
    public ResponseEntity<PipelineSchedule> query(
            @PathVariable("project_id") Integer projectId,
            @RequestParam(name = "user_id",required = false) Integer userId,
            AppExternalConfigDTO appExternalConfigDTO,
            @PathVariable("pipeline_schedule_id") Integer pipelineScheduleId) {
        return ResponseEntity.ok(pipelineScheduleService.query(projectId, userId, appExternalConfigDTO,pipelineScheduleId));
    }

    @ApiOperation(value = "创建定时执行计划变量")
    @PostMapping("{pipeline_schedule_id}/variables")
    public ResponseEntity<Variable> createVariable(
            @ApiParam(value = "项目id", required = true)
            @PathVariable("project_id") Integer projectId,
            @PathVariable("pipeline_schedule_id") Integer pipelineScheduleId,
            @RequestParam(name = "user_id", required = false) Integer userId,
            AppExternalConfigDTO appExternalConfigDTO,
            @RequestBody Variable variable) {
        return ResponseEntity.ok(pipelineScheduleService.createVariable(projectId, pipelineScheduleId, userId, appExternalConfigDTO, variable));
    }

}
