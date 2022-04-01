package io.choerodon.gitlab.app.service.impl;

import java.util.List;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.PipelineSchedule;
import org.gitlab4j.api.models.Variable;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
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

    @Override
    public void update(Integer projectId, Integer userId, AppExternalConfigDTO appExternalConfigDTO, Integer pipelineScheduleId, PipelineSchedule pipelineSchedule) {
        GitLabApi gitLabApi;
        if (appExternalConfigDTO == null || appExternalConfigDTO.getGitlabUrl() == null) {
            gitLabApi = gitlab4jclient.getGitLabApi(userId);
        } else {
            gitLabApi = ExternalGitlabApiUtil.createGitLabApi(appExternalConfigDTO);
        }
        try {
            gitLabApi
                    .getPipelineApi()
                    .updatePipelineSchedule(projectId, pipelineSchedule);
        } catch (GitLabApiException e) {
            throw new CommonException(e);
        }
    }

    @Override
    public void delete(Integer projectId, Integer userId, AppExternalConfigDTO appExternalConfigDTO, Integer pipelineScheduleId) {
        GitLabApi gitLabApi;
        if (appExternalConfigDTO == null || appExternalConfigDTO.getGitlabUrl() == null) {
            gitLabApi = gitlab4jclient.getGitLabApi(userId);
        } else {
            gitLabApi = ExternalGitlabApiUtil.createGitLabApi(appExternalConfigDTO);
        }
        try {
            gitLabApi
                    .getPipelineApi()
                    .deletePipelineSchedule(projectId, pipelineScheduleId);
        } catch (GitLabApiException e) {
            throw new CommonException(e);
        }
    }

    @Override
    public void deleteVariable(Integer projectId, Integer pipelineScheduleId, Integer userId, AppExternalConfigDTO appExternalConfigDTO, Variable variable) {
        GitLabApi gitLabApi;
        if (appExternalConfigDTO == null || appExternalConfigDTO.getGitlabUrl() == null) {
            gitLabApi = gitlab4jclient.getGitLabApi(userId);
        } else {
            gitLabApi = ExternalGitlabApiUtil.createGitLabApi(appExternalConfigDTO);
        }
        try {
            gitLabApi
                    .getPipelineApi()
                    .deletePipelineScheduleVariable(projectId,
                            pipelineScheduleId,
                            variable.getKey());
        } catch (GitLabApiException e) {
            throw new CommonException(e);
        }
    }

    @Override
    public void editVariable(Integer projectId, Integer pipelineScheduleId, Integer userId, AppExternalConfigDTO appExternalConfigDTO, Variable variable) {
        GitLabApi gitLabApi;
        if (appExternalConfigDTO == null || appExternalConfigDTO.getGitlabUrl() == null) {
            gitLabApi = gitlab4jclient.getGitLabApi(userId);
        } else {
            gitLabApi = ExternalGitlabApiUtil.createGitLabApi(appExternalConfigDTO);
        }
        try {
            gitLabApi
                    .getPipelineApi()
                    .updatePipelineScheduleVariable(projectId,
                            pipelineScheduleId,
                            variable.getKey(),
                            variable.getValue());
        } catch (GitLabApiException e) {
            throw new CommonException(e);
        }
    }
}
