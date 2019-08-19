package io.choerodon.gitlab.api.controller.v1;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.gitlab4j.api.models.Issue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.choerodon.core.exception.FeignException;
import io.choerodon.gitlab.api.vo.IssueVO;
import io.choerodon.gitlab.app.service.IssueService;


@RestController
@RequestMapping(value = "/v1/issues")
public class IssueController {

    private IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    /**
     * 创建issue
     *
     * @param issueVO issueDto对象
     * @return Issue
     */
    @ApiOperation(value = "创建issue")
    @PostMapping
    public ResponseEntity<Issue> create(
            @ApiParam(value = "issueDto对象", required = true)
            @RequestBody IssueVO issueVO) {
        return Optional.ofNullable(issueService.createIssue(issueVO))
                .map(target -> new ResponseEntity<>(target, HttpStatus.CREATED))
                .orElseThrow(() -> new FeignException("error.issue.add"));
    }

    /**
     * 批量创建issue
     *
     * @param userId    用户Id  optional
     * @param issueVOs issueDto对象Map
     * @return Map
     */
    @ApiOperation(value = "批量创建issue")
    @PostMapping(value = "/batch_create")
    public ResponseEntity<Map<Long, Issue>> batchCreate(
            @ApiParam(value = "userId")
            @RequestParam(required = false) Integer userId,
            @ApiParam(value = "issue参数map", required = true)
            @RequestBody Map<Long, IssueVO> issueVOs) {

        return Optional.ofNullable(issueService.batchCreateIssue(issueVOs, userId))
                .map(target -> new ResponseEntity<>(target, HttpStatus.CREATED))
                .orElseThrow(() -> new FeignException("error.issue.add"));
    }

    /**
     * 更新issue
     *
     * @param issueVO issueDto对象
     * @return Issue
     */
    @ApiOperation(value = "更新issue")
    @PutMapping
    public ResponseEntity<Issue> update(
            @ApiParam(value = "issue参数map", required = true)
            @RequestBody IssueVO issueVO) {

        return Optional.ofNullable(issueService.updateIssue(issueVO))
                .map(target -> new ResponseEntity<>(target, HttpStatus.CREATED))
                .orElseThrow(() -> new FeignException("error.issue.update"));
    }

    /**
     * 批量更新issue
     *
     * @param userId    用户Id  optional
     * @param issueVOS issueDto对象
     */
    @ApiOperation(value = "批量更新issue")
    @PutMapping(value = "/batch_update")
    public ResponseEntity batchUpdate(
            @ApiParam(value = "用户Id")
            @RequestParam(required = false) Integer userId,
            @ApiParam(value = "issue参数", required = true)
            @RequestBody List<IssueVO> issueVOS) {
        issueService.batchUpdateIssue(userId, issueVOS);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 关闭issue
     *
     * @param projectId 项目id
     * @param issueIid  issueIid
     */
    @ApiOperation(value = "关闭issue")
    @PutMapping(value = "/project/{projectId}/{issueIid}/close")
    public ResponseEntity close(
            @ApiParam(value = "项目id", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "issueIid", required = true)
            @PathVariable Integer issueIid) {
        issueService.closeIssue(projectId, issueIid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 批量关闭issue
     *
     * @param projectId 项目id
     * @param issueIds  issueIds
     */
    @ApiOperation(value = "批量关闭issue")
    @PutMapping(value = "/project/{projectId}/batch_close")
    public ResponseEntity batchClose(
            @ApiParam(value = "项目id", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "userId")
            @RequestParam(required = false) Integer userId,
            @ApiParam(value = "issueIid", required = true)
            @RequestBody List<Integer> issueIds) {
        issueService.batchCloseIssue(projectId, issueIds, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 开启issue
     *
     * @param projectId 项目id
     * @param issueId  issueIid
     */
    @ApiOperation(value = "开启issue")
    @PutMapping(value = "/project/{projectId}/{issueId}/open")
    public ResponseEntity open(
            @ApiParam(value = "项目id", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "issueIid", required = true)
            @PathVariable Integer issueId) {
        issueService.openIssue(projectId, issueId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 批量开启issue
     *
     * @param projectId 项目id
     * @param issueIds  issueIid
     */
    @ApiOperation(value = "批量开启issue")
    @PutMapping(value = "/project/{projectId}/{issueId}/batch_open")
    public ResponseEntity batchOpen(
            @ApiParam(value = "项目id", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "userId")
            @RequestParam(required = false) Integer userId,
            @ApiParam(value = "issueId", required = true)
            @RequestBody List<Integer> issueIds) {
        issueService.batchOpenIssue(projectId, issueIds, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 删除issue
     *
     * @param projectId 项目id
     * @param issueId  issueIid
     */
    @ApiOperation(value = "删除issue")
    @DeleteMapping(value = "/project/{projectId}/{issueId}")
    public ResponseEntity delete(
            @ApiParam(value = "项目id", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "issueId", required = true)
            @PathVariable Integer issueId) {
        issueService.deleteIssue(projectId, issueId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * 批量删除issue
     *
     * @param projectId 项目id
     * @param userId    用户Id  optional
     * @param issueList issueIid List
     */
    @ApiOperation(value = "批量删除issue")
    @DeleteMapping(value = "/project/{projectId}/batch_delete")
    public ResponseEntity batchDeleteIssue(
            @ApiParam(value = "项目id", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "用户Id")
            @RequestParam(required = false) Integer userId,
            @ApiParam(value = "issueId", required = true)
            @RequestBody List<Integer> issueList) {
        issueService.listDeleteIssue(projectId, userId, issueList);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 查询issues
     *
     * @param projectId 项目id
     * @param page      页码
     * @param pageSize  每页大小
     * @return List
     */
    @ApiOperation(value = "查询issues")
    @GetMapping(value = "/project/{projectId}")
    public ResponseEntity<List<Issue>> list(
            @ApiParam(value = "项目id", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "页码")
            @RequestParam Integer page,
            @ApiParam(value = "页数")
            @RequestParam Integer pageSize) {
        return Optional.ofNullable(issueService.listIssues(projectId, page, pageSize))
                .map(target -> new ResponseEntity<>(target, HttpStatus.OK))
                .orElseThrow(() -> new FeignException("error.issues.get"));
    }

    /**
     * 根据issueIid查询单个issue
     *
     * @param projectId 项目id
     * @param issueIid  issueIid
     * @return Issue
     */
    @ApiOperation(value = "根据issueIid查询单个issue")
    @GetMapping(value = "/project/{projectId}/{issueIid}")
    public ResponseEntity<Issue> query(
            @ApiParam(value = "项目id", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "issueIid", required = true)
            @PathVariable Integer issueIid) {

        return Optional.ofNullable(issueService.getIssue(projectId, issueIid))
                .map(target -> new ResponseEntity<>(target, HttpStatus.OK))
                .orElseThrow(() -> new FeignException("error.issue.get"));
    }


}
