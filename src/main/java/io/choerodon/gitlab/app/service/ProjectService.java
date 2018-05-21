package io.choerodon.gitlab.app.service;

import java.util.List;
import java.util.Map;

import org.gitlab4j.api.models.Project;


public interface ProjectService {

    /**
     * 通过项目名称创建项目
     *
     * @param groupId     组 Id
     * @param projectName 项目名
     * @param userName    用户名
     * @return Project
     */
    Project createProject(Integer groupId, String projectName, String userName);

    /**
     * 删除项目
     *
     * @param projectId 项目 id
     * @param userName  用户名
     */
    void deleteProject(Integer projectId, String userName);

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
    Map<String, Object> createVariable(Integer projectId,
                                       String key,
                                       String value,
                                       boolean protecteds,
                                       String userName);

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
    Map<String, Object> createProtectedBranches(Integer projectId,
                                                String name,
                                                String mergeAccessLevel,
                                                String pushAccessLevel,
                                                String userName);

    /**
     * 更新项目
     *
     * @param projectId  项目Id
     * @param userName 用户名
     * @return Project
     */
    Project updateProject(Integer projectId, String userName);

    /**
     * 通过分支名查询保护分支
     *
     * @param projectId 项目Id
     * @param name      分支名
     * @param userName  用户名
     * @return Map
     */
    Map<String, Object> queryBranchByBranchName(Integer projectId, String name, String userName);

    /**
     * 查询保护分支列表
     *
     * @param projectId project id
     * @param userName  user name   optional
     * @return List
     */
    List<Map<String, Object>> listBranch(Integer projectId, String userName);

    /**
     * 通过分支名删除保护分支
     *
     * @param projectId 项目Id
     * @param name      分支名
     * @param userName  用户名
     */
    void deleteByBranchName(Integer projectId, String name, String userName);

}
