package io.choerodon.gitlab.app.service.impl;

import org.springframework.stereotype.Service;

import io.choerodon.core.convertor.ConvertHelper;
import io.choerodon.gitlab.api.dto.GitlabProjectEventDTO;
import io.choerodon.gitlab.app.service.GitlabProjectService;
import io.choerodon.gitlab.domain.event.GitlabProjectEventPayload;
import io.choerodon.gitlab.domain.service.IGitlabProjectService;

/**
 * Created by younger on 2018/3/30.
 */
@Service
public class GitlabProjectServiceImpl implements GitlabProjectService {

    private IGitlabProjectService igitlabProjectService;

    public GitlabProjectServiceImpl(IGitlabProjectService igitlabProjectService) {
        this.igitlabProjectService = igitlabProjectService;
    }


    @Override
    public void createGitlabProject(GitlabProjectEventDTO gitlabProjectEventDTO) {
        igitlabProjectService.createGitlabProject(ConvertHelper.convert(gitlabProjectEventDTO,
                GitlabProjectEventPayload.class));
    }
}
