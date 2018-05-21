package io.choerodon.gitlab.app.service.impl;

import org.gitlab4j.api.models.ProjectHook;
import org.springframework.stereotype.Service;

import io.choerodon.core.exception.CommonException;
import io.choerodon.gitlab.app.service.HookService;
import io.choerodon.gitlab.infra.common.client.Gitlab4jClient;

@Service
public class HookServiceImpl implements HookService {

    private Gitlab4jClient gitlab4jclient;

    public HookServiceImpl(Gitlab4jClient gitlab4jclient) {
        this.gitlab4jclient = gitlab4jclient;
    }

    @Override
    public ProjectHook createProjectHook(Integer projectId, ProjectHook projectHook, String userName) {
        try {
            return gitlab4jclient.getGitLabApi(userName).getProjectApi()
                    .addHook(projectId, projectHook.getUrl(), projectHook, true, projectHook.getToken());
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }
}
