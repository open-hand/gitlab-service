package io.choerodon.gitlab.app.assember;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import io.choerodon.core.convertor.ConvertorI;
import io.choerodon.gitlab.api.dto.GitlabProjectEventDTO;
import io.choerodon.gitlab.domain.event.GitlabProjectEventPayload;

/**
 * Created by younger on 2018/3/29.
 */

@Component
public class GitlabProjectEventAssember implements ConvertorI<GitlabProjectEventPayload, Object, GitlabProjectEventDTO> {

    @Override
    public GitlabProjectEventPayload dtoToEntity(GitlabProjectEventDTO gitlabProjectEventDTO) {
        GitlabProjectEventPayload gitlabProjectEventPayload = new GitlabProjectEventPayload();
        BeanUtils.copyProperties(gitlabProjectEventDTO, gitlabProjectEventPayload);
        return gitlabProjectEventPayload;
    }

}
