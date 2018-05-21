package io.choerodon.gitlab.api.controller.v1;

import java.util.List;
import java.util.Optional;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.gitlab4j.api.models.Label;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.choerodon.core.exception.CommonException;
import io.choerodon.gitlab.api.dto.LabelDto;
import io.choerodon.gitlab.app.service.LabelsService;

@RestController
@RequestMapping("/v1/labels")
public class LabelsController {

    private LabelsService labelsService;

    public LabelsController(LabelsService labelsService) {
        this.labelsService = labelsService;
    }

    /**
     * 查询labels列表
     *
     * @param projectId 项目id
     * @param page      页数
     * @param perPage   每页大小
     * @return List
     */
    @ApiOperation(value = "查询labels列表")
    @GetMapping
    public ResponseEntity<List<Label>> list(
            @ApiParam(value = "项目id", required = true)
            @RequestParam Integer projectId,
            @ApiParam(value = "页数")
            @RequestParam Integer page,
            @ApiParam(value = "每页数量")
            @RequestParam Integer perPage) {
        return Optional.ofNullable(labelsService.listLabels(projectId, page, perPage))
                .map(target -> new ResponseEntity<>(target, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.label.query"));

    }

    /**
     * 创建labels
     *
     * @param labelDto label对象
     * @return Label
     */
    @ApiOperation(value = "创建labels")
    @PostMapping
    public ResponseEntity<Label> create(
            @ApiParam(value = "label对象", required = true)
            @RequestBody LabelDto labelDto) {
        return Optional.ofNullable(labelsService.createLabel(labelDto))
                .map(target -> new ResponseEntity<>(target, HttpStatus.CREATED))
                .orElseThrow(() -> new CommonException("error.label.create"));


    }

    /**
     * 创建labels
     *
     * @param labelDto label对象
     * @return Label
     */
    @ApiOperation(value = "更新labels")
    @PutMapping
    public ResponseEntity<Label> update(
            @ApiParam(value = "label对象", required = true)
            @RequestBody LabelDto labelDto) {
        return Optional.ofNullable(labelsService.updateLabel(labelDto))
                .map(target -> new ResponseEntity<>(target, HttpStatus.CREATED))
                .orElseThrow(() -> new CommonException("error.label.update"));
    }

    /**
     * 删除labels
     *
     * @param projectId 项目id
     * @return Label
     */
    @ApiOperation(value = "删除labels")
    @DeleteMapping
    public ResponseEntity delete(
            @ApiParam(value = "项目id", required = true)
            @RequestParam Integer projectId,
            @ApiParam(value = "名称", required = true)
            @RequestParam String name) {
        labelsService.deleteLabel(projectId, name);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    /**
     * 订阅labels
     *
     * @param projectId 项目id
     * @param labelId   labelID
     * @return Label
     */
    @ApiOperation(value = "订阅labels")
    @PostMapping(value = "/subscribe")
    public ResponseEntity<Label> subscribeLabel(
            @ApiParam(value = "项目id", required = true)
            @RequestParam Integer projectId,
            @ApiParam(value = "labelID", required = true)
            @RequestParam Integer labelId) {
        return Optional.ofNullable(labelsService.subscribeLabel(projectId, labelId))
                .map(target -> new ResponseEntity<>(target, HttpStatus.NOT_MODIFIED))
                .orElseThrow(() -> new CommonException("error.label.subscribe"));

    }

    /**
     * 取消订阅labels
     *
     * @param projectId 项目id
     * @param labelId   labelID
     * @return Label
     */
    @ApiOperation(value = "取消订阅labels")
    @PostMapping(value = "/unsubscribe")
    public ResponseEntity<Label> unSubscribeLabel(
            @ApiParam(value = "项目id", required = true)
            @RequestParam Integer projectId,
            @ApiParam(value = "labelID", required = true)
            @RequestParam Integer labelId) {
        return Optional.ofNullable(labelsService.unSubscribeLabel(projectId, labelId))
                .map(target -> new ResponseEntity<>(target, HttpStatus.NOT_MODIFIED))
                .orElseThrow(() -> new CommonException("error.label.unsubscribe"));

    }
}
