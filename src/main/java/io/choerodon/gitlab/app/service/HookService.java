package io.choerodon.gitlab.app.service;

import org.gitlab4j.api.models.ProjectHook;


public interface HookService {

    /**
     * 创建ProjectHook对象
     *
     * @param projectId   项目id
     * @param userId    用户Id
     * @param projectHook projectHook对象
     * @return ProjectHook
     */

    ProjectHook createProjectHook(Integer projectId, ProjectHook projectHook, Integer userId);
}
