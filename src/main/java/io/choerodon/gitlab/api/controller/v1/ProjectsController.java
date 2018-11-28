package io.choerodon.gitlab.api.controller.v1;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.validation.Valid;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.gitlab4j.api.models.DeployKey;
import org.gitlab4j.api.models.Member;
import org.gitlab4j.api.models.Project;
import org.gitlab4j.api.models.Variable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.choerodon.core.exception.FeignException;
import io.choerodon.gitlab.api.dto.MemberDto;
import io.choerodon.gitlab.app.service.ProjectService;


@RestController
@RequestMapping(value = "/v1/projects")
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
     * @param userId      用户Id
     * @return Project
     */
    @ApiOperation(value = "通过项目名称创建项目")
    @PostMapping
    public ResponseEntity<Project> create(
            @ApiParam(value = "组ID", required = true)
            @RequestParam Integer groupId,
            @ApiParam(value = "项目名称", required = true)
            @RequestParam String projectName,
            @ApiParam(value = "用户Id")
            @RequestParam(required = false) Integer userId,
            @ApiParam(value = "visibility")
            @RequestParam(required = false) boolean visibility) {
        return Optional.ofNullable(projectService.createProject(groupId, projectName, userId, visibility))
                .map(target -> new ResponseEntity<>(target, HttpStatus.CREATED))
                .orElseThrow(() -> new FeignException("error.projects.create.name"));
    }

    /**
     * 删除项目
     *
     * @param projectId 项目 id
     * @param userId    用户Id
     */
    @ApiOperation(value = " 删除项目")
    @DeleteMapping(value = "/{projectId}")
    public ResponseEntity delete(
            @ApiParam(value = "项目ID", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "用户Id称")
            @RequestParam(required = false) Integer userId) {
        projectService.deleteProject(projectId, userId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * 通过group name project name删除项目
     *
     * @param groupName   组名
     * @param projectName 项目名
     * @param userId      用户Id
     */
    @ApiOperation(value = " 删除项目")
    @DeleteMapping(value = "/{groupName}/{projectName}")
    public ResponseEntity delete(
            @ApiParam(value = "gitlab group name", required = true)
            @PathVariable String groupName,
            @ApiParam(value = "项目名", required = true)
            @PathVariable String projectName,
            @ApiParam(value = "用户Id称")
            @RequestParam(required = false) Integer userId) {
        projectService.deleteProjectByName(groupName, projectName, userId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * 增加项目ci环境变量
     *
     * @param projectId  项目Id
     * @param key        变量key
     * @param value      变量值
     * @param protecteds 变量是否保护
     * @param userId     用户Id
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
            @ApiParam(value = "用户Id称")
            @RequestParam(required = false) Integer userId) {
        return Optional.ofNullable(projectService.createVariable(projectId, key, value, protecteds, userId))
                .map(target -> new ResponseEntity<>(target, HttpStatus.CREATED))
                .orElseThrow(() -> new FeignException("error.projects.variable.create"));
    }

    /**
     * 增加项目保护分支
     *
     * @param projectId        项目Id
     * @param name             分支名
     * @param mergeAccessLevel merge权限
     * @param pushAccessLevel  push权限
     * @param userId           userId
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
            @ApiParam(value = "用户Id")
            @RequestParam(required = false) Integer userId) {
        return Optional.ofNullable(projectService.createProtectedBranches(projectId,
                                                                          name, mergeAccessLevel, pushAccessLevel,
                                                                          userId))
                .map(target -> new ResponseEntity<>(target, HttpStatus.CREATED))
                .orElseThrow(() -> new FeignException("error.projects.protected.branches.create"));
    }

    /**
     * 更新项目
     *
     * @param project 项目对象
     * @param userId  用户Id
     * @return Project
     */
    @ApiOperation(value = "更新项目")
    @PutMapping
    public ResponseEntity<Project> update(
            @ApiParam(value = "项目信息", required = true)
            @PathVariable Project project,
            @ApiParam(value = "用户Id", required = true)
            @RequestParam Integer userId) {
        return Optional.ofNullable(projectService.updateProject(project, userId))
                .map(target -> new ResponseEntity<>(target, HttpStatus.OK))
                .orElseThrow(() -> new FeignException("error.projects.update"));
    }

    /**
     * 通过分支名查询保护分支
     *
     * @param projectId 项目Id
     * @param name      分支名
     * @param userId    用户Id
     * @return Map
     */
    @ApiOperation(value = "通过分支名查询保护分支")
    @GetMapping(value = "/{projectId}/protected_branches/{name}")
    public ResponseEntity<Map<String, Object>> queryBranchByBranchName(
            @ApiParam(value = "项目ID", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "保护分支名", required = true)
            @PathVariable String name,
            @ApiParam(value = "用户Id称", required = true)
            @RequestParam Integer userId) {
        return Optional.ofNullable(projectService.queryBranchByBranchName(projectId, name, userId))
                .map(target -> new ResponseEntity<>(target, HttpStatus.OK))
                .orElseThrow(() -> new FeignException("error.protected.branches.get"));
    }

    /**
     * 查询保护分支列表
     *
     * @param projectId 项目Id
     * @param userId    用户Id
     * @return List
     */
    @ApiOperation(value = "查询项目下所有的保护分支")
    @GetMapping(value = "/{projectId}/protected_branches")
    public ResponseEntity<List<Map<String, Object>>> listBranch(
            @ApiParam(value = "项目ID", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "用户Id")
            @RequestParam(required = false) Integer userId) {
        return Optional.ofNullable(projectService.listBranch(projectId, userId))
                .map(target -> new ResponseEntity<>(target, HttpStatus.OK))
                .orElseThrow(() -> new FeignException("error.protected.branches.get"));
    }

    /**
     * 通过分支名删除保护分支
     *
     * @param projectId 项目Id
     * @param name      分支名
     * @param userId    用户Id
     * @return Map
     */
    @ApiOperation(value = "通过分支名删除保护分支")
    @DeleteMapping(value = "/{projectId}/protected_branches/{name}")
    public ResponseEntity deleteByBranchName(
            @ApiParam(value = "项目ID", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "保护分支名", required = true)
            @PathVariable String name,
            @ApiParam(value = "用户Id称", required = true)
            @RequestParam Integer userId) {
        projectService.deleteByBranchName(projectId, name, userId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * 通过项目名称创建项目
     *
     * @param projectId 项目Id
     * @param title     标题
     * @param key       ssh key
     * @param canPush   canPush
     * @param userId    用户Id
     */
    @ApiOperation(value = "通过项目名称创建项目")
    @PostMapping(value = "/deploy_key")
    public ResponseEntity create(
            @ApiParam(value = "项目ID", required = true)
            @RequestParam Integer projectId,
            @ApiParam(value = "标题", required = true)
            @RequestParam String title,
            @ApiParam(value = "ssh key")
            @RequestParam String key,
            @ApiParam(value = "canPush")
            @RequestParam(required = false) boolean canPush,
            @ApiParam(value = "用户Id")
            @RequestParam(required = false) Integer userId) {
        projectService.createDeployKey(projectId, title, key, canPush, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 查询deployKeys
     *
     * @param projectId 项目Id
     * @param userId    用户Id
     * @Return List
     */
    @ApiOperation(value = "查询deployKeys")
    @GetMapping(value = "/deploy_key")
    public ResponseEntity<List<DeployKey>> getDeployKeys(
            @ApiParam(value = "项目ID", required = true)
            @RequestParam Integer projectId,
            @ApiParam(value = "用户Id")
            @RequestParam Integer userId) {
        return Optional.ofNullable(projectService.getDeployKeys(projectId, userId))
                .map(target -> new ResponseEntity<>(target, HttpStatus.OK))
                .orElseThrow(() -> new FeignException("error.project.deploy.key.get"));
    }

    /**
     * 通过项目id查询项目
     *
     * @param projectId 项目id
     * @return Project
     */
    @ApiOperation(value = "通过项目id查询项目")
    @GetMapping(value = "/{project_id}")
    public ResponseEntity<Project> queryProjectById(
            @ApiParam(value = "项目id", required = true)
            @PathVariable(value = "project_id") Integer projectId){
        return Optional.ofNullable(projectService.getProjectById(projectId))
                .map(target -> new ResponseEntity<>(target, HttpStatus.OK))
                .orElseThrow(() -> new FeignException("error.project.get"));
    }

    /**
     * 通过组名项目名查询项目
     *
     * @param userId      项目Id
     * @param groupName   组名
     * @param projectName 项目名
     * @return Project
     */
    @ApiOperation(value = "通过组名项目名查询项目")
    @GetMapping(value = "/queryByName")
    public ResponseEntity<Project> queryByName(
            @ApiParam(value = "用户", required = true)
            @RequestParam Integer userId,
            @ApiParam(value = "组名", required = true)
            @RequestParam String groupName,
            @ApiParam(value = "项目名", required = true)
            @RequestParam String projectName) {
        return Optional.ofNullable(projectService.getProject(userId, groupName, projectName))
                .map(target -> new ResponseEntity<>(target, HttpStatus.OK))
                .orElseThrow(() -> new FeignException("error.project.get"));
    }

    /**
     * 查询Variable
     *
     * @param projectId 项目Id
     * @param userId    组名
     * @return List
     */
    @ApiOperation(value = "查询Variable")
    @GetMapping(value = "/{projectId}/variable")
    public ResponseEntity<List<Variable>> listVariable(
            @ApiParam(value = "用户", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "组名", required = true)
            @RequestParam Integer userId) {
        return Optional.ofNullable(projectService.getVarible(projectId, userId))
                .map(target -> new ResponseEntity<>(target, HttpStatus.OK))
                .orElseThrow(() -> new FeignException("error.Variable.get"));
    }

    /**
     * 添加项目成员
     *
     * @param projectId 项目id
     * @param member    成员信息
     * @return Member
     */
    @ApiOperation(value = "添加项目成员")
    @PostMapping(value = "/{projectId}/members")
    public ResponseEntity<Member> createMember(
            @ApiParam(value = "项目id", required = true)
            @PathVariable(value = "projectId") Integer projectId,
            @ApiParam(value = "成员信息", required = true)
            @RequestBody @Valid MemberDto member) {
        return Optional.ofNullable(projectService.createMember(projectId, member))
                .map(result -> new ResponseEntity<>(result, HttpStatus.CREATED))
                .orElseThrow(() -> new FeignException("error.groups.member.create"));
    }

    /**
     * 移除项目成员
     *
     * @param projectId 项目id
     * @param userId    用户id
     */
    @ApiOperation(value = "移除项目成员")
    @DeleteMapping(value = "/{projectId}/members/{userId}")
    public ResponseEntity deleteMember(
            @ApiParam(value = "项目id", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "用户ID", required = true)
            @PathVariable(value = "userId") Integer userId) {
        projectService.deleteMember(projectId, userId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * 查询项目角色
     *
     * @param projectId 项目id
     * @param userId    用户Id
     * @return Member
     */
    @ApiOperation(value = "查询项目角色")
    @GetMapping(value = "/{projectId}/members/{userId}")
    public ResponseEntity<Member> getMember(
            @ApiParam(value = "项目id", required = true)
            @PathVariable(value = "projectId") Integer projectId,
            @ApiParam(value = "成员信息", required = true)
            @PathVariable(value = "userId") Integer userId) {
        return Optional.ofNullable(projectService.getMember(projectId, userId))
                .map(result -> new ResponseEntity<>(result, HttpStatus.CREATED))
                .orElseThrow(() -> new FeignException("error.groups.member.create"));
    }

    /**
     * 获取项目下所有成员
     *
     * @param projectId 项目id
     * @return List
     */
    @ApiOperation(value = "获取项目下所有成员")
    @GetMapping(value = "/{project_id}/members/list")
    public ResponseEntity<List<Member>> getAllMemberByProjectId(
            @ApiParam(value = "项目id", required = true)
            @PathVariable(value = "project_id") Integer projectId) {
        return Optional.ofNullable(projectService.getAllMemberByProjectId(projectId))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new FeignException("error.project.member.list"));
    }

    /**
     * 获取用户的gitlab项目列表
     *
     * @param userId 用户id
     * @return List
     */
    @ApiParam(value = "获取用户的gitlab项目列表")
    @GetMapping(value = "/{user_id}/projects")
    public ResponseEntity<List<Project>> getMemberProjects(
            @ApiParam(value = "用户id", required = true)
            @PathVariable(value = "user_id") Integer userId) {
        return Optional.ofNullable(projectService.getMemberProjects(userId))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new FeignException("error.member.projects.list"));
    }
}
