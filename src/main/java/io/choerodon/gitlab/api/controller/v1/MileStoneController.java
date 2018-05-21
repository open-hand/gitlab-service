package io.choerodon.gitlab.api.controller.v1;

import java.util.List;
import java.util.Optional;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.gitlab4j.api.models.Milestone;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.choerodon.core.exception.CommonException;
import io.choerodon.gitlab.api.dto.MileStoneDto;
import io.choerodon.gitlab.app.service.MileStoneService;

@RestController
@RequestMapping("/v1/milestones")
public class MileStoneController {

    private MileStoneService mileStoneService;

    public MileStoneController(MileStoneService mileStoneService) {
        this.mileStoneService = mileStoneService;
    }

    /**
     * 创建milestone
     *
     * @param mileStoneDto milestone对象
     * @return Milestone
     */
    @ApiOperation(value = "创建milestone")
    @PostMapping
    public ResponseEntity<Milestone> create(
            @ApiParam(value = "milestone参数", required = true)
            @RequestBody MileStoneDto mileStoneDto
    ) {
        return Optional.ofNullable(mileStoneService.createMilestone(mileStoneDto))
                .map(target -> new ResponseEntity<>(target, HttpStatus.CREATED))
                .orElseThrow(() -> new CommonException("error.milestone.create"));
    }

    /**
     * 关闭milestone
     *
     * @param projectId   项目id
     * @param milestoneId milestoneId
     * @return Milestone
     */
    @ApiOperation(value = "关闭milestone")
    @PutMapping(value = "/{projectId}/{milestoneId}/close")
    public ResponseEntity<Milestone> closeMilestone(
            @ApiParam(value = "项目id", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "milestoneId", required = true)
            @PathVariable Integer milestoneId) {
        return Optional.ofNullable(mileStoneService.closeMilestone(projectId, milestoneId))
                .map(target -> new ResponseEntity<>(target, HttpStatus.CREATED))
                .orElseThrow(() -> new CommonException("error.milestone.close"));
    }

    /**
     * 激活milestone
     *
     * @param projectId   项目id
     * @param milestoneId milestoneId
     * @return Milestone
     */
    @ApiOperation(value = "激活milestone")
    @PutMapping(value = "/{projectId}/{milestoneId}/active")
    public ResponseEntity<Milestone> activeMilestone(
            @ApiParam(value = "项目id", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "milestoneId", required = true)
            @PathVariable Integer milestoneId) {
        return Optional.ofNullable(mileStoneService.activeMilestone(projectId, milestoneId))
                .map(target -> new ResponseEntity<>(target, HttpStatus.CREATED))
                .orElseThrow(() -> new CommonException("error.milestone.activate"));
    }


    /**
     * 更新milestone
     *
     * @param mileStoneDto mileStoneDto对象
     * @return Milestone
     */
    @ApiOperation(value = "更新milestone")
    @PutMapping
    public ResponseEntity<Milestone> update(
            @ApiParam(value = "milestone参数", required = true)
            @RequestBody MileStoneDto mileStoneDto) {
        return Optional.ofNullable(mileStoneService.updateMilestone(mileStoneDto))
                .map(target -> new ResponseEntity<>(target, HttpStatus.CREATED))
                .orElseThrow(() -> new CommonException("error.milestone.update"));
    }

    /**
     * 查询milestones列表
     *
     * @param page      页码
     * @param perPage   每页大小
     * @param projectId 项目Id
     * @return List
     */
    @ApiOperation(value = "查询milestones列表")
    @GetMapping
    public ResponseEntity<List<Milestone>> list(
            @ApiParam(value = "页码")
            @RequestParam Integer page,
            @ApiParam(value = "分页大小")
            @RequestParam Integer perPage,
            @ApiParam(value = "projectId", required = true)
            @RequestBody Integer projectId) {
        return Optional.ofNullable(mileStoneService.listMilestones(projectId, page, perPage))
                .map(target -> new ResponseEntity<>(target, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.milestones.query"));

    }

    /**
     * 通过projectId,milestoneState和search查询milestones列表
     *
     * @param mileStoneDto MileStoneDto对象
     * @return List
     */
    @ApiOperation(value = "通过projectId,milestoneState和search查询milestones列表")
    @GetMapping("/options")
    public ResponseEntity<List<Milestone>> listByOptions(
            @ApiParam(value = "mileStoneDto", required = true)
            @RequestBody MileStoneDto mileStoneDto) {
        return Optional.ofNullable(mileStoneService.listMileStoneByOptions(mileStoneDto))
                .map(target -> new ResponseEntity<>(target, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.milestones.query"));

    }

    /**
     * 查询单个mileStone信息
     *
     * @param projectId   项目id
     * @param milestoneId milestoneId
     * @return Milestone
     */
    @ApiOperation(value = "查询milestone")
    @GetMapping(value = "/{milestoneId}")
    public ResponseEntity<Milestone> query(
            @ApiParam(value = "项目id", required = true)
            @RequestParam Integer projectId,
            @ApiParam(value = "milestoneId", required = true)
            @PathVariable Integer milestoneId) {
        return Optional.ofNullable(mileStoneService.queryMilestone(projectId, milestoneId))
                .map(target -> new ResponseEntity<>(target, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.milestone.query"));
    }
}
