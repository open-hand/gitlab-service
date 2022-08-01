package io.choerodon.gitlab.api.controller.v1;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.gitlab4j.api.models.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import io.choerodon.core.exception.FeignException;
import io.choerodon.gitlab.api.vo.GitlabTransferVO;
import io.choerodon.gitlab.api.vo.GroupVO;
import io.choerodon.gitlab.api.vo.MemberVO;
import io.choerodon.gitlab.api.vo.VariableVO;
import io.choerodon.gitlab.app.service.GroupService;

@RestController
@RequestMapping(value = "/v1/groups")
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
    @ApiOperation(value = "查询有权限的所有组")
    @PostMapping("/{userId}")
    public ResponseEntity<List<Group>> list(@ApiParam(value = "userId")
                                            @PathVariable(value = "userId") Integer userId,
                                            @RequestParam(value = "owned", required = false) Boolean owned,
                                            @RequestParam(value = "search", required = false) String search,
                                            @RequestBody List<Integer> skipGroups) {
        return ResponseEntity.ok(groupService.listGroupsWithParam(userId, owned, search, skipGroups));
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
                .orElseThrow(() -> new FeignException("error.groups.list"));
    }

    /**
     * 创建组
     *
     * @param group  组对象
     * @param userId 用户Id
     * @return Group
     */
    @ApiOperation(value = "创建组")
    @PostMapping
    public ResponseEntity<GroupVO> create(
            @ApiParam(value = "组对象信息", required = true)
            @RequestBody @Valid GroupVO group,
            @ApiParam(value = "用户userId")
            @RequestParam(required = false) Integer userId) {
        return Optional.ofNullable(groupService.createGroup(group, userId))
                .map(result -> new ResponseEntity<>(result, HttpStatus.CREATED))
                .orElseThrow(() -> new FeignException("error.groups.create"));
    }

    @ApiOperation(value = "更新组")
    @PutMapping(value = "/{groupId}")
    public ResponseEntity update(
            @ApiParam(value = "组ID", required = true)
            @PathVariable Integer groupId,
            @ApiParam(value = "用户userId")
            @RequestParam(required = false) Integer userId,
            @ApiParam(value = "组对象信息", required = true)
            @RequestBody @Valid Group group) {
        return Optional.ofNullable(groupService.updateGroup(groupId, userId, group))
                .map(result -> new ResponseEntity<>(result, HttpStatus.CREATED))
                .orElseThrow(() -> new FeignException("error.groups.update"));
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
            @PathVariable Integer groupId,
            @ApiParam(value = "用户userId")
            @RequestParam(required = false) Integer userId) {
        groupService.deleteGroup(groupId, userId);
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
                .orElseThrow(() -> new FeignException("error.groups.members.get"));
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
                .orElseThrow(() -> new FeignException("error.groups.member.get"));
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
            @RequestBody @Valid MemberVO member) {
        return Optional.ofNullable(groupService.createMember(groupId, member))
                .map(result -> new ResponseEntity<>(result, HttpStatus.CREATED))
                .orElseThrow(() -> new FeignException("error.groups.member.create"));
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
            @RequestBody @Valid MemberVO member) {
        return Optional.ofNullable(groupService.updateMember(groupId, member))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new FeignException("error.groups.member.update"));
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
     * @param groupId 组对象Id
     * @param userId  用户Id
     * @return List
     */
    @ApiOperation(value = "获取项目列表")
    @GetMapping(value = "/{groupId}/projects/event")
    public ResponseEntity<List<Project>> listProjects(
            @ApiParam(value = "组ID", required = true)
            @PathVariable Integer groupId,
            @ApiParam(value = "userId")
            @RequestParam(required = false) Integer userId,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "perPage", required = false) Integer perPage) {
        return ResponseEntity.ok(groupService.listProjects(groupId, userId, page, perPage));
    }

    /**
     * 获取项目列表
     *
     * @param groupId 组对象Id
     * @param userId  用户Id
     * @return List
     */
    @ApiOperation(value = "获取项目列表")
    @GetMapping(value = "/{groupId}/projects")
    public ResponseEntity<List<Project>> listProjects(
            @PathVariable(value = "groupId") Integer groupId,
            @RequestParam(value = "userId", required = false) Integer userId,
            @RequestParam(value = "owned", required = false) Boolean owned,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "perPage", required = false) Integer perPage,
            @RequestParam(value = "minAccessLevel", required = false) Integer minAccessLevel) {
        return ResponseEntity.ok(groupService.listProjects(groupId, userId, owned, search, page, perPage, minAccessLevel));
    }

    /**
     * 根据组名查询组
     *
     * @param groupName 组名
     * @param userId    用户Id
     * @return group
     */
    @ApiOperation(value = "根据组名查询组")
    @GetMapping(value = "/{groupName}")
    public ResponseEntity<Group> queryGroupByName(
            @ApiParam(value = "组名", required = true)
            @PathVariable String groupName,
            @ApiParam(value = "userId")
            @RequestParam(required = false) Integer userId
    ) {
        return new ResponseEntity<>(groupService.queryGroupByName(groupName, userId), HttpStatus.OK);
    }

    @ApiOperation(value = "根据组id查询组")
    @GetMapping(value = "/{group_iid}")
    public ResponseEntity<Group> queryGroupByIid(
            @ApiParam(value = "组名", required = true)
            @PathVariable(value = "group_iid") Integer groupIid,
            @ApiParam(value = "userId")
            @RequestParam(required = false) Integer userId) {
        return new ResponseEntity<>(groupService.queryGroupByIid(groupIid, userId), HttpStatus.OK);
    }

    @ApiOperation(value = "根据组名检索组的列表")
    @GetMapping(value = "/{group_name}/with_statistics")
    public ResponseEntity<List<Group>> queryGroupWithStatisticsByName(
            @ApiParam(value = "组名", required = true)
            @PathVariable(value = "group_name") String groupName,
            @ApiParam(value = "userId")
            @RequestParam(required = false) Integer userId,
            @RequestParam(value = "statistics", defaultValue = "false") Boolean statistics) {
        return new ResponseEntity<>(groupService.queryGroupWithStatisticsByName(groupName, userId, statistics), HttpStatus.OK);
    }


    @ApiOperation(value = "查出组下所有的AccessRequest")
    @GetMapping(value = "/{groupId}/access_requests")
    public ResponseEntity<List<AccessRequest>> listAccessRequestsOfGroup(
            @ApiParam("组id")
            @PathVariable("groupId") Integer groupId) {
        return new ResponseEntity<>(groupService.listAccessRequests(groupId), HttpStatus.OK);
    }

    /**
     * 这个接口不抛出关于GitlabApi的异常
     *
     * @param groupId 组id
     * @param userId  被拒绝的用户的id
     * @return OK
     */
    @ApiOperation(value = "拒绝组下某个人的AccessRequest请求")
    @DeleteMapping(value = "/{groupId}/access_requests")
    public ResponseEntity denyAccessRequest(
            @ApiParam(value = "组id")
            @PathVariable("groupId") Integer groupId,
            @ApiParam(value = "被拒绝的用户id")
            @RequestParam("user_id") Integer userId) {
        groupService.denyAccessRequest(groupId, userId);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation(value = "查询组Variable")
    @GetMapping(value = "/{groupId}/variable")
    public ResponseEntity<List<Variable>> listGroupVariable(
            @ApiParam(value = "用户", required = true)
            @PathVariable Integer groupId,
            @ApiParam(value = "组名", required = true)
            @RequestParam Integer userId) {
        return Optional.ofNullable(groupService.getGroupVariable(groupId, userId))
                .map(target -> new ResponseEntity<>(target, HttpStatus.OK))
                .orElseThrow(() -> new FeignException("error.group.variable.get"));
    }

    @ApiOperation(value = "添加组Variable")
    @PostMapping(value = "/{groupId}/variable")
    public ResponseEntity<Variable> createGroupVariable(
            @ApiParam(value = "组ID", required = true)
            @PathVariable Integer groupId,
            @ApiParam(value = "变量key&value", required = true)
            @RequestBody @Validated({GitlabTransferVO.AddCiProjectVariable.class}) GitlabTransferVO gitlabTransferVO,
            @ApiParam(value = "变量是否保护", required = true)
            @RequestParam boolean protecteds,
            @ApiParam(value = "用户Id称")
            @RequestParam(required = false) Integer userId) {
        return Optional.ofNullable(groupService.createVariable(groupId, gitlabTransferVO.getKey(), gitlabTransferVO.getValue(), protecteds, userId))
                .map(target -> new ResponseEntity<>(target, HttpStatus.OK))
                .orElseThrow(() -> new FeignException("error.group.variable.create"));
    }


    /**
     * 批量增加/更新项目ci环境变量
     *
     * @param groupId 组Id
     * @param list    variable信息
     * @return Map
     */
    @ApiOperation(value = " 批量增加/更新组ci环境变量")
    @PutMapping(value = "/{groupId}/variables")
    public ResponseEntity<List<Variable>> batchSaveVariable(
            @ApiParam(value = "组ID", required = true)
            @PathVariable Integer groupId,
            @ApiParam(value = "用户ID", required = true)
            @RequestParam(value = "userId") Integer userId,
            @ApiParam(value = "variable信息", required = true)
            @RequestBody @Valid List<VariableVO> list) {
        return Optional.ofNullable(groupService.batchCreateVariable(groupId, list, userId))
                .map(target -> new ResponseEntity<>(target, HttpStatus.CREATED))
                .orElseThrow(() -> new FeignException("error.group.variable.batch.create"));
    }

    /**
     * 批量删除组中指定key的变量
     *
     * @param groupId 组id
     * @param userId  用户id
     * @param key     key
     * @return 204 code
     */
    @ApiOperation(value = "批量删除组中指定key的变量")
    @DeleteMapping(value = "/{groupId}/variables")
    public ResponseEntity<Void> batchDeleteVariable(
            @ApiParam(value = "组ID", required = true)
            @PathVariable Integer groupId,
            @ApiParam(value = "用户ID", required = true)
            @RequestParam(value = "userId") Integer userId,
            @ApiParam(value = "variable keys", required = true)
            @RequestBody List<String> key) {
        groupService.batchDeleteVariable(groupId, key, userId);
        return ResponseEntity.noContent().build();
    }
}
