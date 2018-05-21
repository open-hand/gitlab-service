package io.choerodon.gitlab.domain.service.impl;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Project;
import org.gitlab4j.api.models.Visibility;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.choerodon.core.exception.CommonException;
import io.choerodon.event.producer.execute.EventProducerTemplate;
import io.choerodon.gitlab.domain.event.GitlabProjectEventPayload;
import io.choerodon.gitlab.domain.service.IGitlabProjectService;
import io.choerodon.gitlab.infra.common.client.Gitlab4jClient;

/**
 * Created by younger on 2018/3/30.
 */
@Service
public class IGitlabProjectServiceImpl implements IGitlabProjectService {

    private static final String DEVELOP = "develop";
    private static final String MASTER = "master";
    private static final String TEMPLATE = "template";
    private static final String APPLICATION = "application";

    @Value("${spring.application.name}")
    private String applicationName;

    private Gitlab4jClient gitlab4jclient;
    private EventProducerTemplate eventProducerTemplate;

    public IGitlabProjectServiceImpl(Gitlab4jClient gitlab4jclient, EventProducerTemplate eventProducerTemplate) {
        this.gitlab4jclient = gitlab4jclient;
        this.eventProducerTemplate = eventProducerTemplate;
    }

    @Override
    public void createGitlabProject(GitlabProjectEventPayload gitlabProjectEventPayload) {
        GitLabApi gitLabApi = gitlab4jclient.getGitLabApi(gitlabProjectEventPayload.getUserName());
        try {
            Project project = gitLabApi.getProjectApi().createProject(gitlabProjectEventPayload.getGroupId(),
                    gitlabProjectEventPayload.getPath());
            if (gitlabProjectEventPayload.getType().equals(TEMPLATE)) {
                project.setVisibility(Visibility.PUBLIC);
            }
            project.setPublic(true);
            gitLabApi.getProjectApi().updateProject(project);
            gitlabProjectEventPayload.setGitlabProjectId(project.getId());
            Exception exception = eventProducerTemplate.execute(
                    "OperationGitlabProject",
                    "devops-service",
                    gitlabProjectEventPayload,
                    (String uuid) -> {
                    });
            if (exception != null) {
                throw new CommonException(exception.getMessage());
            }
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage());
        }
    }

}
