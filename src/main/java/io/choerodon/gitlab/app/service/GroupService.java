package io.choerodon.gitlab.app.service;

import io.choerodon.gitlab.api.vo.GroupVO;
import io.choerodon.gitlab.api.vo.MemberVO;
import io.choerodon.gitlab.api.vo.VariableVO;
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
     * 查询所有组
     *
     * @return List
     */
    List<Group> listGroupsWithParam(GroupFilter groupFilter, Integer userId);


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
     * @param page
     * @param perPage
     * @return List
     */
    List<Project> listProjects(Integer groupId, Integer userId, Integer page, Integer perPage);

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
     * @param groupId 组id
     * @param userId  用户id
     * @return list
     */
    List<Variable> getGroupVariable(Integer groupId, Integer userId);


    /**
     * 增加组ci环境变量
     *
     * @param groupId    组id
     * @param key        变量key
     * @param value      变量值
     * @param protecteds 变量是否保护
     * @param userId     用户Id
     * @return
     */
    Variable createVariable(Integer groupId,
                            String key,
                            String value,
                            boolean protecteds,
                            Integer userId);

    /**
     * 删除组ci环境变量
     *
     * @param groupId 组id
     * @param key     变量key
     * @param userId  用户id
     */
    void deleteVariable(Integer groupId,
                        String key,
                        Integer userId);

    /**
     * 批量删除组ci环境变量
     *
     * @param groupId 组id
     * @param keys    变量keys
     * @param userId  用户id
     */
    void batchDeleteVariable(Integer groupId,
                             List<String> keys,
                             Integer userId);

    /**
     * 批量增加组ci环境变量
     *
     * @param groupId 组id
     * @param list    变量信息
     * @param userId  用户id
     * @return
     */
    List<Variable> batchCreateVariable(Integer groupId,
                                       List<VariableVO> list,
                                       Integer userId);

    List<Project> listProjects(Integer groupId, Integer userId, GroupProjectsFilter filter);
}
