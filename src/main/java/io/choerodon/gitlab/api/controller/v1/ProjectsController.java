package io.choerodon.gitlab.api.controller.v1;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.gitlab4j.api.models.Project;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.choerodon.core.exception.CommonException;
import io.choerodon.gitlab.app.service.ProjectService;


@RestController
@RequestMapping("/v1/projects")
public class ProjectsController {

    private ProjectService projectService;

    public ProjectsController(ProjectService projectService) {
        this.projectService = projectService;
    }

    /**
     * 通过项目名称创建项目
     *
     * @param groupId     组 Id
     * @param projectName 项目名
     * @param userName    用户名
     * @return Project
     */
    @ApiOperation(value = "通过项目名称创建项目")
    @PostMapping
    public ResponseEntity<Project> create(
            @ApiParam(value = "组ID", required = true)
            @RequestParam Integer groupId,
            @ApiParam(value = "项目名称", required = true)
            @RequestParam String projectName,
            @ApiParam(value = "用户名称")
            @RequestParam(required = false) String userName) {
        return Optional.ofNullable(projectService.createProject(groupId, projectName, userName))
                .map(target -> new ResponseEntity<>(target, HttpStatus.CREATED))
                .orElseThrow(() -> new CommonException("error.projects.create.name"));
    }


    /**
     * 删除项目
     *
     * @param projectId 项目 id
     * @param userName  用户名
     */
    @ApiOperation(value = " 删除项目")
    @DeleteMapping(value = "/{projectId}")
    public ResponseEntity delete(
            @ApiParam(value = "项目ID", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "用户名称")
            @RequestParam(required = false) String userName) {
        projectService.deleteProject(projectId, userName);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * 增加项目ci环境变量
     *
     * @param projectId  项目Id
     * @param key        变量key
     * @param value      变量值
     * @param protecteds 变量是否保护
     * @param userName   用户名
     * @return Map
     */
    @ApiOperation(value = "增加项目ci环境变量")
    @PostMapping(value = "/{projectId}/variables")
    public ResponseEntity<Map<String, Object>> saveVariableEvent(
            @ApiParam(value = "项目ID", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "变量key", required = true)
            @RequestParam String key,
            @ApiParam(value = "变量值", required = true)
            @RequestParam String value,
            @ApiParam(value = "变量是否保护", required = true)
            @RequestParam boolean protecteds,
            @ApiParam(value = "用户名称")
            @RequestParam(required = false) String userName) {
        return Optional.ofNullable(projectService.createVariable(projectId, key, value, protecteds, userName))
                .map(target -> new ResponseEntity<>(target, HttpStatus.CREATED))
                .orElseThrow(() -> new CommonException("error.projects.variable.create"));
    }

    /**
     * 增加项目保护分支
     *
     * @param projectId        项目Id
     * @param name             分支名
     * @param mergeAccessLevel merge权限
     * @param pushAccessLevel  push权限
     * @param userName         user name
     * @return Map
     */
    @ApiOperation(value = "增加项目保护分支")
    @PostMapping(value = "/{projectId}/protected_branches")
    public ResponseEntity<Map<String, Object>> createProtectedBranches(
            @ApiParam(value = "项目ID", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "分支名", required = true)
            @RequestParam String name,
            @ApiParam(value = "merge权限", required = true)
            @RequestParam String mergeAccessLevel,
            @ApiParam(value = "push权限", required = true)
            @RequestParam String pushAccessLevel,
            @ApiParam(value = "用户名称")
            @RequestParam(required = false) String userName) {
        return Optional.ofNullable(projectService.createProtectedBranches(projectId,
                name, mergeAccessLevel, pushAccessLevel, userName))
                .map(target -> new ResponseEntity<>(target, HttpStatus.CREATED))
                .orElseThrow(() -> new CommonException("error.projects.protected.branches.create"));
    }

    /**
     * 更新项目
     *
     * @param projectId  项目对象
     * @param userName 用户名
     * @return Project
     */
    @ApiOperation(value = "更新项目")
    @PutMapping("/{projectId}")
    public ResponseEntity<Project> update(
            @ApiParam(value = "项目信息", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "用户名称", required = true)
            @RequestParam String userName) {
        return Optional.ofNullable(projectService.updateProject(projectId, userName))
                .map(target -> new ResponseEntity<>(target, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.projects.update"));
    }

    /**
     * 通过分支名查询保护分支
     *
     * @param projectId 项目Id
     * @param name      分支名
     * @param userName  用户名
     * @return Map
     */
    @ApiOperation(value = "通过分支名查询保护分支")
    @GetMapping(value = "/{projectId}/protected_branches/{name}")
    public ResponseEntity<Map<String, Object>> queryBranchByBranchName(
            @ApiParam(value = "项目ID", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "保护分支名", required = true)
            @PathVariable String name,
            @ApiParam(value = "用户名称", required = true)
            @RequestParam String userName) {
        return Optional.ofNullable(projectService.queryBranchByBranchName(projectId, name, userName))
                .map(target -> new ResponseEntity<>(target, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.protected.branches.get"));
    }

    /**
     * 查询保护分支列表
     *
     * @param projectId 项目Id
     * @param userName  用户名
     * @return List
     */
    @ApiOperation(value = "查询项目下所有的保护分支")
    @GetMapping(value = "/{projectId}/protected_branches")
    public ResponseEntity<List<Map<String, Object>>> listBranch(
            @ApiParam(value = "项目ID", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "用户名称")
            @RequestParam(required = false) String userName) {
        return Optional.ofNullable(projectService.listBranch(projectId, userName))
                .map(target -> new ResponseEntity<>(target, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.protected.branches.get"));
    }

    /**
     * 通过分支名删除保护分支
     *
     * @param projectId 项目Id
     * @param name      分支名
     * @param userName  用户名
     * @return Map
     */
    @ApiOperation(value = "通过分支名删除保护分支")
    @DeleteMapping(value = "/{projectId}/protected_branches/{name}")
    public ResponseEntity deleteByBranchName(
            @ApiParam(value = "项目ID", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "保护分支名", required = true)
            @PathVariable String name,
            @ApiParam(value = "用户名称", required = true)
            @RequestParam String userName) {
        projectService.deleteByBranchName(projectId, name, userName);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
