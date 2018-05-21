package io.choerodon.gitlab.api.eventhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import io.choerodon.core.event.EventPayload;
import io.choerodon.event.consumer.annotation.EventListener;
import io.choerodon.gitlab.api.dto.GitlabProjectEventDTO;
import io.choerodon.gitlab.app.service.GitlabProjectService;

/**
 * Created by younger on 2018/4/10.
 */
@Component
public class ProjectEventHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectEventHandler.class);

    private GitlabProjectService gitlabProjectService;

    public ProjectEventHandler(GitlabProjectService gitlabProjectService) {
        this.gitlabProjectService = gitlabProjectService;
    }

    /**
     * 创建GitLabProject
     *
     * @param payload kafka消息
     */
    @EventListener(topic = "gitlab-service", businessType = "CreateGitlabProject")
    public void handleGitlabProjectEvent(EventPayload<GitlabProjectEventDTO> payload) {

        GitlabProjectEventDTO gitlabProjectEventDTO = payload.getData();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.info("data: {}", gitlabProjectEventDTO);
        }
        gitlabProjectService.createGitlabProject(gitlabProjectEventDTO);
    }
}
