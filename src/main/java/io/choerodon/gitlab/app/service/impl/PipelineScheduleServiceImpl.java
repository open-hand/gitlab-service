package io.choerodon.gitlab.app.service.impl;

import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.PipelineSchedule;
import org.springframework.stereotype.Service;

import io.choerodon.core.exception.CommonException;
import io.choerodon.gitlab.app.service.PipelineScheduleService;
import io.choerodon.gitlab.infra.common.client.Gitlab4jClient;

/**
 * 〈功能简述〉
 * 〈〉
 *
 * @author wanghao
 * @since 2022/3/23 15:28
 */
@Service
public class PipelineScheduleServiceImpl implements PipelineScheduleService {

    private Gitlab4jClient gitlab4jclient;

    @Override
    public PipelineSchedule create(Integer projectId, Integer userId, PipelineSchedule pipelineSchedule) {

        try {
            return gitlab4jclient.getGitLabApi(userId).getPipelineApi().createPipelineSchedule(projectId, pipelineSchedule);
        } catch (GitLabApiException e) {
            throw new CommonException(e);
        }
    }
}
