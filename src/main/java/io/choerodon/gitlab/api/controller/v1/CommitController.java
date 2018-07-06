package io.choerodon.gitlab.api.controller.v1;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.gitlab4j.api.models.Commit;
import org.gitlab4j.api.models.CommitStatuse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.choerodon.core.exception.CommonException;
import io.choerodon.gitlab.app.service.CommitService;

/**
 * Created by zzy on 2018/1/14.
 */
@RestController
@RequestMapping("/v1/projects/{projectId}/repository/commits")
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
                .orElseThrow(() -> new CommonException("error.commit.get"));
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
    @GetMapping("/statuse")
    public ResponseEntity<List<CommitStatuse>> getCommitStatuse(
            @ApiParam(value = "项目id", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "sha", required = true)
            @RequestParam String sha,
            @ApiParam(value = "userId")
            @RequestParam(required = false) Integer userId) {
        return Optional.ofNullable(commitService.getCommitStatuse(projectId, sha, userId))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.commit.get"));
    }

    /**
     * 查询某个项目的某个分支的所有commit
     *
     * @param projectId  项目ID
     * @param branchName 分支名称
     * @param since      创建时间
     * @return commit列表
     */

    @ApiOperation(value = "查询某个项目的某个分支的所有commit")
    @GetMapping("/branch")
    public ResponseEntity<List<Commit>> getCommits(
            @ApiParam(value = "项目id", required = true)
            @PathVariable(value = "projectId") Integer projectId,
            @ApiParam(value = "分支名称", required = true)
            @RequestParam String branchName,
            @ApiParam(value = "分支创建时间", required = true)
            @RequestParam Date since) {
        return Optional.ofNullable(commitService.getCommits(projectId, branchName, since))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.commits.get"));
    }
}

