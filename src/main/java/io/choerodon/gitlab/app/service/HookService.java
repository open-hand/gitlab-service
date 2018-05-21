package io.choerodon.gitlab.app.service;

import org.gitlab4j.api.models.ProjectHook;


public interface HookService {

    /**
     * 创建ProjectHook对象
     *
     * @param projectId   项目id
     * @param userName    用户名
     * @param projectHook projectHook对象
     * @return ProjectHook
     */

    ProjectHook createProjectHook(Integer projectId, ProjectHook projectHook, String userName);
}
