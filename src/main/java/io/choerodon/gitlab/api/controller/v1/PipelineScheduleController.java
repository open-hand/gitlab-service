package io.choerodon.gitlab.api.controller.v1;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.gitlab4j.api.models.PipelineSchedule;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.choerodon.gitlab.app.service.PipelineScheduleService;

@RestController
@RequestMapping(value = "/v1/projects/{projectId}/pipeline_schedules")
public class PipelineScheduleController {

    private PipelineScheduleService pipelineScheduleService;


    @ApiOperation(value = "创建定时执行计划")
    @PostMapping
    public ResponseEntity<PipelineSchedule> create(
            @ApiParam(value = "项目id", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "用户Id")
            @RequestParam(required = false) Integer userId,
            @RequestBody PipelineSchedule pipelineSchedule) {
        return ResponseEntity.ok(pipelineScheduleService.create(projectId, userId, pipelineSchedule));
    }

}
