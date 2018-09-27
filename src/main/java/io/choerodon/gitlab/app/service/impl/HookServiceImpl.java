package io.choerodon.gitlab.app.service.impl;

import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.ProjectHook;
import org.springframework.stereotype.Service;

import io.choerodon.core.exception.FeignException;
import io.choerodon.gitlab.app.service.HookService;
import io.choerodon.gitlab.infra.common.client.Gitlab4jClient;

@Service
public class HookServiceImpl implements HookService {

    private Gitlab4jClient gitlab4jclient;


    public HookServiceImpl(Gitlab4jClient gitlab4jclient) {
        this.gitlab4jclient = gitlab4jclient;
    }

    @Override
    public ProjectHook createProjectHook(Integer projectId, ProjectHook projectHook, Integer userId) {
        try {
            return gitlab4jclient.getGitLabApi(userId).getProjectApi()
                    .addHook(projectId, projectHook.getUrl(), projectHook, true, projectHook.getToken());
        } catch (Exception e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public ProjectHook updateProjectHook(Integer projectId, Integer hookId, Integer userId) {
        try {
            ProjectHook projectHook = gitlab4jclient.getGitLabApi(userId).getProjectApi().getHook(projectId, hookId);
            projectHook.setPipelineEvents(true);
            projectHook.setJobEvents(true);
            return gitlab4jclient.getGitLabApi(userId).getProjectApi().modifyHook(projectHook);
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e);
        }
    }
}
