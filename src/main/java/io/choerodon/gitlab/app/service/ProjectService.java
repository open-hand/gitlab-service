package io.choerodon.gitlab.app.service;

import java.util.List;
import java.util.Map;

import org.gitlab4j.api.models.DeployKey;
import org.gitlab4j.api.models.Member;
import org.gitlab4j.api.models.Project;
import org.gitlab4j.api.models.Variable;

import io.choerodon.gitlab.api.dto.MemberDto;


public interface ProjectService {

    /**
     * 通过项目名称创建项目
     *
     * @param groupId     组 Id
     * @param projectName 项目名
     * @param userId      用户Id
     * @return Project
     */
    Project createProject(Integer groupId, String projectName, Integer userId, boolean visibility);

    /**
     * 删除项目
     *
     * @param projectId 项目 id
     * @param userId    用户名
     */
    void deleteProject(Integer projectId, Integer userId);


    /**
     * 通过group名和项目名删除项目
     *
     * @param groupName   项目 id
     * @param userId      用户名
     * @param projectName 项目名
     */
    void deleteProjectByName(String groupName, String projectName, Integer userId);

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
    Map<String, Object> createVariable(Integer projectId,
                                       String key,
                                       String value,
                                       boolean protecteds,
                                       Integer userId);

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
    Map<String, Object> createProtectedBranches(Integer projectId,
                                                String name,
                                                String mergeAccessLevel,
                                                String pushAccessLevel,
                                                Integer userId);

    /**
     * 更新项目
     *
     * @param project 项目Id
     * @param userId  用户Id
     * @return Project
     */
    Project updateProject(Project project, Integer userId);

    /**
     * 通过分支名查询保护分支
     *
     * @param projectId 项目Id
     * @param name      分支名
     * @param userId    用户名
     * @return Map
     */
    Map<String, Object> queryBranchByBranchName(Integer projectId, String name, Integer userId);

    /**
     * 查询保护分支列表
     *
     * @param projectId project id
     * @param userId    userId   optional
     * @return List
     */
    List<Map<String, Object>> listBranch(Integer projectId, Integer userId);

    /**
     * 通过分支名删除保护分支
     *
     * @param projectId 项目Id
     * @param name      分支名
     * @param userId    用户名
     */
    void deleteByBranchName(Integer projectId, String name, Integer userId);


    /**
     * 通过项目名称创建项目
     *
     * @param projectId 项目Id
     * @param title     标题
     * @param key       ssh key
     * @param canPush   canPush
     * @param userId    用户Id
     */
    void createDeployKey(Integer projectId, String title, String key, boolean canPush, Integer userId);

    /**
     * 通过项目id查询项目
     *
     * @param projectId 项目id
     * @return Project
     */
    Project getProjectById(Integer projectId);

    /**
     * 通过组名项目名查询项目
     *
     * @param userId      项目Id
     * @param groupCode   组名
     * @param projectCode 项目名
     * @return Project
     */
    Project getProject(Integer userId, String groupCode, String projectCode);


    /**
     * 查询Variable
     *
     * @param projectId 项目id
     * @param userId    用户id
     * @return list
     */
    List<Variable> getVarible(Integer projectId, Integer userId);

    /**
     * 添加项目成员
     *
     * @param projectId 项目id
     * @param member    成员信息
     * @return Member
     */
    Member createMember(Integer projectId, MemberDto member);

    /**
     * 移除项目成员
     *
     * @param projectId 项目id
     * @param userId    用户id
     */
    void deleteMember(Integer projectId, Integer userId);

    /**
     * 查询deployKeys
     *
     * @param projectId 项目Id
     * @param userId    用户Id
     * @Return List
     */
    List<DeployKey> getDeployKeys(Integer projectId, Integer userId);

    /**
     * 查询项目角色
     *
     * @param projectId 项目id
     * @param userId    用户Id
     * @return Member
     */
    Member getMember(Integer projectId, Integer userId);

    /**
     * 获取项目下所有成员
     *
     * @param projectId 项目id
     * @return List
     */
    List<Member> getAllMemberByProjectId(Integer projectId);


    /**
     * 获取用户的gitlab项目列表
     *
     * @param userId 用户id
     * @return List
     */
    List<Project> getMemberProjects(Integer userId);
}
