package io.choerodon.gitlab.app.service;

import io.choerodon.gitlab.api.vo.GroupVO;
import io.choerodon.gitlab.api.vo.MemberVO;
import org.gitlab4j.api.models.*;

import java.util.List;

public interface GroupService {
    /**
     * 查询所有组
     *
     * @return List
     */
    List<Group> listGroups();


    /**
     * 创建组
     *
     * @param group  组对象
     * @param userId 用户名
     * @return Group
     */
    GroupVO createGroup(GroupVO group, Integer userId);


    /**
     * 更新组
     *
     * @param groupId 组对象Id
     * @param userId  用户名
     * @param group   组对象
     * @return Group
     */
    Group updateGroup(Integer groupId, Integer userId, Group group);

    /**
     * 删除组
     *
     * @param groupId 组对象Id
     */
    void deleteGroup(Integer groupId, Integer userId);

    /**
     * 查询组中的成员
     *
     * @param groupId 组对象Id
     * @return List
     */
    List<Member> listMember(Integer groupId);

    /**
     * 根据用户ID获得组成员信息
     *
     * @param groupId 组对象Id
     * @param userId  用户Id
     * @return Member
     */
    Member queryMemberByUserId(Integer groupId, Integer userId);

    /**
     * 增加组成员
     *
     * @param groupId 组对象Id
     * @param member  成员信息
     * @return Member
     */
    Member createMember(Integer groupId, MemberVO member);

    /**
     * 更新组成员
     *
     * @param groupId 组对象Id
     * @param member  成员信息
     * @return Member
     */
    Member updateMember(Integer groupId, MemberVO member);

    /**
     * 移除组成员
     *
     * @param groupId 组对象Id
     * @param userId  成员信息
     */
    void deleteMember(Integer groupId, Integer userId);

    /**
     * 获取项目列表
     *
     * @param groupId 组对象Id
     * @param userId  用户名
     * @return List
     */
    List<Project> listProjects(Integer groupId, Integer userId);

    /**
     * 根据组名查询组
     *
     * @param groupName 组名
     * @param userId    用户Id
     * @return group
     */
    Group queryGroupByName(String groupName, Integer userId);

    /**
     * 查出组下所有的AccessRequest
     *
     * @param groupId 组id
     * @return 所有的AccessRequest列表
     */
    List<AccessRequest> listAccessRequests(Integer groupId);

    /**
     * 拒绝组下某个人的AccessRequest
     *
     * @param groupId          组id
     * @param userIdToBeDenied 被拒绝的人的id
     */
    void denyAccessRequest(Integer groupId, Integer userIdToBeDenied);


    /**
     * 查询组Variable
     *
     * @param groupId 项目id
     * @param userId  用户id
     * @return list
     */
    List<Variable> getGroupVariable(Integer groupId, Integer userId);
}
