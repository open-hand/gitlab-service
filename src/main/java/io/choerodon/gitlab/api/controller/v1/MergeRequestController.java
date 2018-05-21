package io.choerodon.gitlab.api.controller.v1;

import java.util.List;
import java.util.Optional;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.gitlab4j.api.models.Commit;
import org.gitlab4j.api.models.MergeRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.choerodon.core.exception.CommonException;
import io.choerodon.gitlab.app.service.MergeRequestService;

@RestController
@RequestMapping("/v1/projects/{projectId}/merge_requests")
public class MergeRequestController {

    private static final String ERROR_MERGE_REQUEST_CREATE = "error.mergeRequest.create";
    private MergeRequestService mergeRequestService;

    public MergeRequestController(MergeRequestService mergeRequestService) {
        this.mergeRequestService = mergeRequestService;
    }

    /**
     * 创建merge请求
     *
     * @param projectId    工程ID
     * @param sourceBranch 源分支
     * @param targetBranch 目标分支
     * @param title        标题
     * @param description  描述
     * @param username     用户名
     * @return MergeRequest
     */
    @ApiOperation(value = "创建merge请求")
    @PostMapping
    public ResponseEntity<MergeRequest> create(
            @ApiParam(value = "工程id", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "要创建的分支名", required = true)
            @RequestParam("sourceBranch") String sourceBranch,
            @ApiParam(value = "源分支名", required = true)
            @RequestParam("targetBranch") String targetBranch,
            @ApiParam(value = "源分支名", required = true)
            @RequestParam("title") String title,
            @ApiParam(value = "源分支名", required = true)
            @RequestParam("description") String description,
            @ApiParam(value = "用户名")
            @RequestParam(value = "username", required = false) String username) {
        return Optional.ofNullable(mergeRequestService.createMergeRequest(projectId,
                sourceBranch, targetBranch, title, description, username))
                .map(result -> new ResponseEntity<>(result, HttpStatus.CREATED))
                .orElseThrow(() -> new CommonException(ERROR_MERGE_REQUEST_CREATE));
    }

    /**
     * 刷新合并请求merge_status
     *
     * @param projectId      项目id
     * @param mergeRequestId 合并请求id
     * @param username       用户名
     */
    @ApiOperation(value = "刷新合并请求merge_status")
    @PutMapping("/{mergeRequestId}")
    public ResponseEntity update(
            @ApiParam(value = "项目id", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "合并请求id", required = true)
            @PathVariable Integer mergeRequestId,
            @ApiParam(value = "用户名")
            @RequestParam(value = "username", required = false) String username) {
        mergeRequestService.updateMergeRequest(projectId, mergeRequestId, username);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 获取合并请求merge request
     *
     * @param projectId      项目id
     * @param mergeRequestId 合并请求id
     * @param username       用户名
     * @return MergeRequest
     */
    @ApiOperation(value = "获取合并请求merge request")
    @GetMapping("/{mergeRequestId}")
    public ResponseEntity<MergeRequest> query(
            @ApiParam(value = "项目id", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "合并请求id", required = true)
            @PathVariable("mergeRequestId") Integer mergeRequestId,
            @ApiParam(value = "用户名")
            @RequestParam(value = "username", required = false) String username) {
        return Optional.ofNullable(mergeRequestService.queryMergeRequest(projectId, mergeRequestId, username))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException(ERROR_MERGE_REQUEST_CREATE));
    }

    /**
     * 获取合并请求列表merge request
     *
     * @param projectId 项目id
     * @return MergeRequest
     */
    @ApiOperation(value = "获取合并请求列表merge request")
    @GetMapping
    public ResponseEntity<List<MergeRequest>> list(
            @ApiParam(value = "工程id", required = true)
            @PathVariable Integer projectId) {
        return Optional.ofNullable(mergeRequestService.listMergeRequests(projectId))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException(ERROR_MERGE_REQUEST_CREATE));
    }

    /**
     * 执行merge请求
     *
     * @param projectId                 项目id
     * @param mergeRequestId            merge请求的id
     * @param mergeCommitMessage        merge的commit信息
     * @param shouldRemoveSourceBranch  merge后是否删除该分支
     * @param mergeWhenPipelineSucceeds pipeline成功后自动合并分支
     * @param username                  用户名
     * @return MergeRequest
     */
    @ApiOperation(value = "执行merge请求")
    @PutMapping("/{mergeRequestId}/merge")
    public ResponseEntity<MergeRequest> acceptMergeRequest(
            @ApiParam(value = "项目id", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "merge请求的id", required = true)
            @PathVariable Integer mergeRequestId,
            @ApiParam(value = "merge的commit信息", required = true)
            @RequestParam("mergeCommitMessage") String mergeCommitMessage,
            @ApiParam(value = "merge后是否删除该分支")
            @RequestParam("removeSourceBranch") Boolean shouldRemoveSourceBranch,
            @ApiParam(value = "pipeline成功后自动合并分支")
            @RequestParam("mergeWhenPipelineSucceeds") Boolean mergeWhenPipelineSucceeds,
            @ApiParam(value = "用户名")
            @RequestParam(value = "username", required = false) String username) {
        return Optional.ofNullable(mergeRequestService.acceptMergeRequest(projectId,
                mergeRequestId, mergeCommitMessage, shouldRemoveSourceBranch, mergeWhenPipelineSucceeds, username))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.mergeRequest.accept"));
    }

    /**
     * 查询合并请求的commits
     *
     * @param projectId      项目id
     * @param mergeRequestId 合并请求ID
     * @return List
     */
    @ApiOperation(value = "查询合并请求的commits")
    @GetMapping("/{mergeRequestId}/commit")
    public ResponseEntity<List<Commit>> listCommits(
            @ApiParam(value = "项目ID", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "合并请求ID", required = true)
            @PathVariable Integer mergeRequestId) {
        return Optional.ofNullable(mergeRequestService.listCommits(projectId, mergeRequestId))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.mergeRequest.merge"));
    }

    /**
     * 删除合并请求
     *
     * @param projectId      项目id
     * @param mergeRequestId 合并请求ID
     * @return List
     */
    @ApiOperation(value = "删除合并请求")
    @DeleteMapping("{mergeRequestId}")
    public ResponseEntity delete(@PathVariable Integer projectId, @PathVariable Integer mergeRequestId) {
        mergeRequestService.deleteMergeRequest(projectId, mergeRequestId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
