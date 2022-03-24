package io.choerodon.gitlab.app.service.impl;

import java.util.List;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.PipelineSchedule;
import org.gitlab4j.api.models.Variable;
import org.springframework.stereotype.Service;

import io.choerodon.core.exception.CommonException;
import io.choerodon.gitlab.app.service.PipelineScheduleService;
import io.choerodon.gitlab.infra.common.client.Gitlab4jClient;
import io.choerodon.gitlab.infra.dto.AppExternalConfigDTO;
import io.choerodon.gitlab.infra.util.ExternalGitlabApiUtil;

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
    public PipelineSchedule create(Integer projectId, Integer userId, AppExternalConfigDTO appExternalConfigDTO, PipelineSchedule pipelineSchedule) {
        GitLabApi gitLabApi;
        if (appExternalConfigDTO == null || appExternalConfigDTO.getGitlabUrl() == null) {
            gitLabApi = gitlab4jclient.getGitLabApi(userId);
        } else {
            gitLabApi = ExternalGitlabApiUtil.createGitLabApi(appExternalConfigDTO);
        }
        try {
            return gitLabApi.getPipelineApi().createPipelineSchedule(projectId, pipelineSchedule);
        } catch (GitLabApiException e) {
            throw new CommonException(e);
        }
    }

    @Override
    public Variable createVariable(Integer projectId, Integer pipelineScheduleId, Integer userId, AppExternalConfigDTO appExternalConfigDTO, Variable variable) {
        GitLabApi gitLabApi;
        if (appExternalConfigDTO == null || appExternalConfigDTO.getGitlabUrl() == null) {
            gitLabApi = gitlab4jclient.getGitLabApi(userId);
        } else {
            gitLabApi = ExternalGitlabApiUtil.createGitLabApi(appExternalConfigDTO);
        }
        try {
            return gitLabApi
                    .getPipelineApi()
                    .createPipelineScheduleVariable(projectId,
                            pipelineScheduleId,
                            variable.getKey(),
                            variable.getValue());
        } catch (GitLabApiException e) {
            throw new CommonException(e);
        }
    }



    @Override
    public PipelineSchedule query(Integer projectId, Integer userId, AppExternalConfigDTO appExternalConfigDTO, Integer pipelineScheduleId) {
        GitLabApi gitLabApi;
        if (appExternalConfigDTO == null || appExternalConfigDTO.getGitlabUrl() == null) {
            gitLabApi = gitlab4jclient.getGitLabApi(userId);
        } else {
            gitLabApi = ExternalGitlabApiUtil.createGitLabApi(appExternalConfigDTO);
        }
        try {
            return gitLabApi
                    .getPipelineApi()
                    .getPipelineSchedule(projectId,
                            pipelineScheduleId);
        } catch (GitLabApiException e) {
            throw new CommonException(e);
        }
    }

    @Override
    public List<PipelineSchedule> list(Integer projectId, Integer userId, AppExternalConfigDTO appExternalConfigDTO) {
        GitLabApi gitLabApi;
        if (appExternalConfigDTO == null || appExternalConfigDTO.getGitlabUrl() == null) {
            gitLabApi = gitlab4jclient.getGitLabApi(userId);
        } else {
            gitLabApi = ExternalGitlabApiUtil.createGitLabApi(appExternalConfigDTO);
        }
        try {
            return gitLabApi
                    .getPipelineApi()
                    .getPipelineSchedules(projectId);
        } catch (GitLabApiException e) {
            throw new CommonException(e);
        }
    }
}
