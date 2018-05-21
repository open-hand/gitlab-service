package io.choerodon.gitlab.api.controller.v1;

import java.util.Optional;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.gitlab4j.api.models.Commit;
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
     * @param userName  用户名
     * @return commit 信息
     */
    @ApiOperation(value = "查询某个commit的具体信息")
    @GetMapping
    public ResponseEntity<Commit> getPipeline(
            @ApiParam(value = "项目id", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "sha", required = true)
            @RequestParam String sha,
            @ApiParam(value = "userName")
            @RequestParam(required = false) String userName) {
        return Optional.ofNullable(commitService.getCommit(projectId, sha, userName))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.commit.get"));
    }
}

