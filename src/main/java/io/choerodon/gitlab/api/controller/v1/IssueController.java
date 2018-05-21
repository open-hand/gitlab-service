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

import io.choerodon.core.exception.CommonException;
import io.choerodon.gitlab.api.dto.IssueDto;
import io.choerodon.gitlab.app.service.IssueService;


@RestController
@RequestMapping("/v1/issues")
public class IssueController {

    private IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    /**
     * 创建issue
     *
     * @param issueDto issueDto对象
     * @return Issue
     */
    @ApiOperation(value = "创建issue")
    @PostMapping
    public ResponseEntity<Issue> create(
            @ApiParam(value = "issueDto对象", required = true)
            @RequestBody IssueDto issueDto) {
        return Optional.ofNullable(issueService.createIssue(issueDto))
                .map(target -> new ResponseEntity<>(target, HttpStatus.CREATED))
                .orElseThrow(() -> new CommonException("error.issue.add"));
    }

    /**
     * 批量创建issue
     *
     * @param userName  用户名  optional
     * @param issueDtos issueDto对象Map
     * @return Map
     */
    @ApiOperation(value = "批量创建issue")
    @PostMapping(value = "/batch_create")
    public ResponseEntity<Map<Long, Issue>> batchCreate(
            @ApiParam(value = "userName")
            @RequestParam(required = false) String userName,
            @ApiParam(value = "issue参数map", required = true)
            @RequestBody Map<Long, IssueDto> issueDtos) {

        return Optional.ofNullable(issueService.batchCreateIssue(issueDtos, userName))
                .map(target -> new ResponseEntity<>(target, HttpStatus.CREATED))
                .orElseThrow(() -> new CommonException("error.issue.add"));
    }

    /**
     * 更新issue
     *
     * @param issueDto issueDto对象
     * @return Issue
     */
    @ApiOperation(value = "更新issue")
    @PutMapping
    public ResponseEntity<Issue> update(
            @ApiParam(value = "issue参数map", required = true)
            @RequestBody IssueDto issueDto) {

        return Optional.ofNullable(issueService.updateIssue(issueDto))
                .map(target -> new ResponseEntity<>(target, HttpStatus.CREATED))
                .orElseThrow(() -> new CommonException("error.issue.update"));
    }

    /**
     * 批量更新issue
     *
     * @param userName  用户名  optional
     * @param issueDtos issueDto对象
     */
    @ApiOperation(value = "批量更新issue")
    @PutMapping(value = "/batch_update")
    public ResponseEntity batchUpdate(
            @ApiParam(value = "用户名")
            @RequestParam(required = false) String userName,
            @ApiParam(value = "issue参数", required = true)
            @RequestBody List<IssueDto> issueDtos) {
        issueService.batchUpdateIssue(userName, issueDtos);
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
            @ApiParam(value = "userName")
            @RequestParam(required = false) String userName,
            @ApiParam(value = "issueIid", required = true)
            @RequestBody List<Integer> issueIds) {
        issueService.batchCloseIssue(projectId, issueIds, userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 开启issue
     *
     * @param projectId 项目id
     * @param issueIid  issueIid
     */
    @ApiOperation(value = "开启issue")
    @PutMapping(value = "/project/{projectId}/{issueIid}/open")
    public ResponseEntity open(
            @ApiParam(value = "项目id", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "issueIid", required = true)
            @PathVariable Integer issueIid) {
        issueService.openIssue(projectId, issueIid);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 批量开启issue
     *
     * @param projectId 项目id
     * @param issueIds  issueIid
     */
    @ApiOperation(value = "批量开启issue")
    @PutMapping(value = "/project/{projectId}/{issueIid}/batch_open")
    public ResponseEntity batchOpen(
            @ApiParam(value = "项目id", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "userName")
            @RequestParam(required = false) String userName,
            @ApiParam(value = "issueIid", required = true)
            @RequestBody List<Integer> issueIds) {
        issueService.batchOpenIssue(projectId, issueIds, userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 删除issue
     *
     * @param projectId 项目id
     * @param issueIid  issueIid
     */
    @ApiOperation(value = "删除issue")
    @DeleteMapping(value = "/project/{projectId}/{issueIid}")
    public ResponseEntity delete(
            @ApiParam(value = "项目id", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "issueIid", required = true)
            @PathVariable Integer issueIid) {
        issueService.deleteIssue(projectId, issueIid);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * 批量删除issue
     *
     * @param projectId 项目id
     * @param userName  用户名  optional
     * @param issueList issueIid List
     */
    @ApiOperation(value = "批量删除issue")
    @DeleteMapping(value = "/project/{projectId}/batch_delete")
    public ResponseEntity batchDeleteIssue(
            @ApiParam(value = "项目id", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "用户名")
            @RequestParam(required = false) String userName,
            @ApiParam(value = "issueIid", required = true)
            @RequestBody List<Integer> issueList) {
        issueService.listDeleteIssue(projectId, userName, issueList);
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
    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<Issue>> list(
            @ApiParam(value = "项目id", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "页码")
            @RequestParam Integer page,
            @ApiParam(value = "页数")
            @RequestParam Integer pageSize) {
        return Optional.ofNullable(issueService.listIssues(projectId, page, pageSize))
                .map(target -> new ResponseEntity<>(target, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.issues.get"));
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
                .orElseThrow(() -> new CommonException("error.issue.get"));
    }


}
