package io.choerodon.gitlab.api.controller.v1;

import java.util.List;
import java.util.Optional;

import io.choerodon.gitlab.api.vo.CommitVO;
import io.choerodon.gitlab.api.vo.GitlabTransferVO;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.gitlab4j.api.models.Commit;
import org.gitlab4j.api.models.CommitPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import io.choerodon.core.exception.FeignException;
import io.choerodon.gitlab.api.vo.CommitStatuseVO;
import io.choerodon.gitlab.app.service.CommitService;

import javax.validation.Valid;

/**
 * Created by zzy on 2018/1/14.
 */
@RestController
@RequestMapping(value = "/v1/projects/{projectId}/repository/commits")
public class CommitController {

    @Autowired
    private CommitService commitService;

    /**
     * 查询某个commit的具体信息
     *
     * @param projectId 项目 ID
     * @param sha       COMMIT SHA
     * @param userId    用户Id
     * @return commit 信息
     */
    @ApiOperation(value = "查询某个commit的具体信息")
    @GetMapping
    public ResponseEntity<Commit> getPipeline(
            @ApiParam(value = "项目id", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "sha", required = true)
            @RequestParam String sha,
            @ApiParam(value = "userId")
            @RequestParam(required = false) Integer userId) {
        return Optional.ofNullable(commitService.getCommit(projectId, sha, userId))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new FeignException("error.commit.get"));
    }


    /**
     * 查询某个commit的Statuse
     *
     * @param projectId 项目 ID
     * @param sha       COMMIT SHA
     * @param userId    用户Id
     * @return List
     */
    @ApiOperation(value = "查询某个commit的Statuse")
    @GetMapping(value = "/statuse")
    public ResponseEntity<List<CommitStatuseVO>> getCommitStatuse(
            @ApiParam(value = "项目id", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "sha", required = true)
            @RequestParam String sha,
            @ApiParam(value = "userId")
            @RequestParam(required = false) Integer userId) {
        return Optional.ofNullable(commitService.getCommitStatuse(projectId, sha, userId))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new FeignException("error.commit.get"));
    }

    /**
     * 查询某个项目的某个分支的所有commit
     *
     * @param projectId 项目ID
     * @return commit列表
     */

    @ApiOperation(value = "查询某个项目的某个分支的所有commit")
    @PostMapping(value = "/branch")
    public ResponseEntity<List<Commit>> getCommits(
            @ApiParam(value = "项目id", required = true)
            @PathVariable(value = "projectId") Integer projectId,
            @ApiParam(value = "分支名称和创建时间", required = true)
            @RequestBody @Validated({GitlabTransferVO.GetCommits.class}) GitlabTransferVO gitlabTransferVO) {
        return Optional.ofNullable(commitService.getCommits(projectId, gitlabTransferVO.getBranchName(), gitlabTransferVO.getSince()))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new FeignException("error.commits.get"));
    }


    /**
     * 查询某个项目的所有commit
     *
     * @param projectId 项目ID
     * @param userId    用户名
     * @param page      page
     * @param size      size
     *                  * @return commit列表
     */

    @ApiOperation(value = "查询某个项目的所有commit")
    @GetMapping(value = "/project")
    public ResponseEntity<List<Commit>> listCommits(
            @ApiParam(value = "项目id", required = true)
            @PathVariable(value = "projectId") Integer projectId,
            @ApiParam(value = "page")
            @RequestParam Integer page,
            @ApiParam(value = "size")
            @RequestParam Integer size,
            @ApiParam(value = "用户名", required = true)
            @RequestParam Integer userId) {
        return Optional.ofNullable(commitService.listCommits(projectId, page, size, userId))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new FeignException("error.commits.get"));
    }


    @PostMapping
    @ApiOperation("创建commit，可以批量操作文件")
    public ResponseEntity createCommit(
            @ApiParam(value = "项目id", required = true)
            @PathVariable(value = "projectId") Integer projectId,
            @ApiParam(value = "用户名", required = true)
            @RequestParam(value = "user_id") Integer userId,
            @ApiParam(value = "操作文件相关的信息")
            @RequestBody CommitPayload commitPayload) {
        commitService.createCommit(projectId, userId, commitPayload);
        return new ResponseEntity(HttpStatus.OK);
    }
}

