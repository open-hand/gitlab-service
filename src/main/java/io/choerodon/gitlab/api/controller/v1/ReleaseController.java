package io.choerodon.gitlab.api.controller.v1;

import java.util.Optional;

import io.swagger.annotations.ApiOperation;
import org.gitlab4j.api.models.Release;
import org.gitlab4j.api.models.ReleaseParams;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.choerodon.core.exception.FeignException;
import io.choerodon.gitlab.app.service.ReleaseService;


@RestController
@RequestMapping(value = "/v1/projects/{project_id}")
public class ReleaseController {

    private final ReleaseService releaseService;

    public ReleaseController(ReleaseService releaseService) {
        this.releaseService = releaseService;
    }

    /**
     * 通过项目名称创建项目
     *
     * @param groupId     组 Id
     * @param projectName 项目名
     * @param userId      用户Id
     * @return Project
     */
    @ApiOperation(value = "")
    @PostMapping("/releases")
    public ResponseEntity<Release> create(
            @PathVariable(value = "project_id") Integer projectId,
            @RequestParam(required = false) Integer userId,
            @RequestBody(required = false) ReleaseParams release) {
        return Optional.ofNullable(releaseService.create(projectId, userId, release))
                .map(target -> new ResponseEntity<>(target, HttpStatus.CREATED))
                .orElseThrow(() -> new FeignException("error.projects.create.name"));
    }


}
