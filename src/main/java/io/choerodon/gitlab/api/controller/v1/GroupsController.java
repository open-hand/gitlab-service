package io.choerodon.gitlab.api.controller.v1;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.gitlab4j.api.models.Group;
import org.gitlab4j.api.models.Member;
import org.gitlab4j.api.models.Project;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.choerodon.core.exception.CommonException;
import io.choerodon.gitlab.api.dto.GroupDTO;
import io.choerodon.gitlab.api.dto.MemberDto;
import io.choerodon.gitlab.app.service.GroupService;

@RestController
@RequestMapping("/v1/groups")
public class GroupsController {

    private GroupService groupService;

    public GroupsController(GroupService groupService) {
        this.groupService = groupService;
    }

    /**
     * 查询所有组
     *
     * @return List
     */
    @ApiOperation(value = "查询所有组")
    @GetMapping
    public ResponseEntity<List<Group>> list() {
        return Optional.ofNullable(groupService.listGroups())
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.groups.list"));
    }

    /**
     * 创建组
     *
     * @param group    组对象
     * @param userName 用户名
     * @return Group
     */
    @ApiOperation(value = "创建组")
    @PostMapping
    public ResponseEntity<GroupDTO> create(
            @ApiParam(value = "组对象信息", required = true)
            @RequestBody @Valid GroupDTO group,
            @ApiParam(value = "用户username")
            @RequestParam(required = false) String userName) {
        return Optional.ofNullable(groupService.createGroup(group, userName))
                .map(result -> new ResponseEntity<>(result, HttpStatus.CREATED))
                .orElseThrow(() -> new CommonException("error.groups.create"));
    }

    /**
     * 更新组
     *
     * @param groupId  组对象Id
     * @param group    组对象
     * @param userName 用户名
     * @return Group
     */
    @ApiOperation(value = "更新组")
    @PutMapping(value = "/{groupId}")
    public ResponseEntity update(
            @ApiParam(value = "组ID", required = true)
            @PathVariable Integer groupId,
            @ApiParam(value = "用户username")
            @RequestParam(required = false) String userName,
            @ApiParam(value = "组对象信息", required = true)
            @RequestBody @Valid Group group) {
        return Optional.ofNullable(groupService.updateGroup(groupId, userName, group))
                .map(result -> new ResponseEntity<>(result, HttpStatus.CREATED))
                .orElseThrow(() -> new CommonException("error.groups.update"));
    }

    /**
     * 删除组
     *
     * @param groupId 组对象Id
     */
    @ApiOperation(value = "删除组")
    @DeleteMapping(value = "/{groupId}")
    public ResponseEntity delete(
            @ApiParam(value = "组ID", required = true)
            @PathVariable Integer groupId) {
        groupService.deleteGroup(groupId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * 查询组中的成员
     *
     * @param groupId 组对象Id
     * @return List
     */
    @ApiOperation(value = "获得组成员列表")
    @GetMapping(value = "/{groupId}/members")
    public ResponseEntity<List<Member>> listMember(
            @ApiParam(value = "组ID", required = true)
            @PathVariable Integer groupId) {
        return Optional.ofNullable(groupService.listMember(groupId))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.groups.members.get"));
    }

    /**
     * 根据用户ID获得组成员信息
     *
     * @param groupId 组对象Id
     * @param userId  用户Id
     * @return Member
     */
    @ApiOperation(value = "根据用户ID获得组成员信息")
    @GetMapping(value = "/{groupId}/members/{userId}")
    public ResponseEntity<Member> queryMemberByUserId(
            @ApiParam(value = "组ID", required = true)
            @PathVariable Integer groupId,
            @ApiParam(value = "用户ID", required = true)
            @PathVariable Integer userId) {
        return Optional.ofNullable(groupService.queryMemberByUserId(groupId, userId))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.groups.member.get"));
    }

    /**
     * 增加组成员
     *
     * @param groupId 组对象Id
     * @param member  成员信息
     * @return Member
     */
    @ApiOperation(value = "增加组成员")
    @PostMapping(value = "/{groupId}/members")
    public ResponseEntity<Member> createMember(
            @ApiParam(value = "组ID", required = true)
            @PathVariable Integer groupId,
            @ApiParam(value = "成员信息", required = true)
            @RequestBody @Valid MemberDto member) {
        return Optional.ofNullable(groupService.createMember(groupId, member))
                .map(result -> new ResponseEntity<>(result, HttpStatus.CREATED))
                .orElseThrow(() -> new CommonException("error.groups.member.create"));
    }

    /**
     * 更新组成员
     *
     * @param groupId 组对象Id
     * @param member  成员信息
     * @return Member
     */
    @ApiOperation(value = "修改组成员")
    @PutMapping(value = "/{groupId}/members")
    public ResponseEntity<Member> updateMember(
            @ApiParam(value = "组ID", required = true)
            @PathVariable Integer groupId,
            @ApiParam(value = "成员信息", required = true)
            @RequestBody @Valid io.choerodon.gitlab.api.dto.MemberDto member) {
        return Optional.ofNullable(groupService.updateMember(groupId, member))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.groups.member.update"));
    }

    /**
     * 移除组成员
     *
     * @param groupId 组对象Id
     * @param userId  成员信息
     */
    @ApiOperation(value = "移除组成员")
    @DeleteMapping(value = "/{groupId}/members/{userId}")
    public ResponseEntity deleteMember(
            @ApiParam(value = "组ID", required = true)
            @PathVariable Integer groupId,
            @ApiParam(value = "用户ID", required = true)
            @PathVariable Integer userId) {
        groupService.deleteMember(groupId, userId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    /**
     * 获取项目列表
     *
     * @param groupId  组对象Id
     * @param userName 用户名
     * @return List
     */
    @ApiOperation(value = "获取项目列表")
    @GetMapping(value = "/{groupId}/projects/event")
    public ResponseEntity<List<Project>> listProjects(
            @ApiParam(value = "组ID", required = true)
            @PathVariable Integer groupId,
            @ApiParam(value = "userName")
            @RequestParam(required = false) String userName) {
        return Optional.ofNullable(groupService.listProjects(groupId, userName))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.groups.project.query"));
    }

    /**
     * 根据组名查询组
     *
     * @param groupName 组名
     * @return group
     */
    @ApiOperation(value = "根据组名查询组")
    @GetMapping(value = "/{groupName}")
    public ResponseEntity<Group> queryGroupByName(
            @ApiParam(value = "组名", required = true)
            @PathVariable String groupName
    ) {
        return new ResponseEntity<>(groupService.queryGroupByName(groupName), HttpStatus.OK);
    }
}
