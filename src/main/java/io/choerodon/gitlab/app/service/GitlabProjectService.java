package io.choerodon.gitlab.app.service;

import io.choerodon.gitlab.api.dto.GitlabProjectEventDTO;

/**
 * Created by younger on 2018/3/30.
 */
public interface GitlabProjectService {
    void createGitlabProject(GitlabProjectEventDTO gitlabProjectEventDTO);
}
