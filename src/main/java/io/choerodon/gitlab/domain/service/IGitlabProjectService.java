package io.choerodon.gitlab.domain.service;

import io.choerodon.gitlab.domain.event.GitlabProjectEventPayload;

/**
 * Created by younger on 2018/3/30.
 */
public interface IGitlabProjectService {
    void createGitlabProject(GitlabProjectEventPayload gitlabProjectEventPayload);
}
