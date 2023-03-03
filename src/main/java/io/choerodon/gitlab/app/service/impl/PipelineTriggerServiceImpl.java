package io.choerodon.gitlab.app.service.impl;

import java.util.List;

import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.PipelineTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.choerodon.core.exception.FeignException;
import io.choerodon.gitlab.app.service.PipelineTriggerService;
import io.choerodon.gitlab.infra.common.client.Gitlab4jClient;

@Service
public class PipelineTriggerServiceImpl implements PipelineTriggerService {

    @Autowired
    private Gitlab4jClient gitlab4jClient;

    @Override
    public PipelineTrigger createPipelineTrigger(Integer projectId, Integer userId, String description) {
        try {
            return gitlab4jClient.getGitLabApi(userId).getPipelineTriggerApi().createPipelineTrigger(projectId, description);
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e.getHttpStatus());
        }
    }

    @Override
    public void deletePipelineTrigger(Integer projectId, Integer userId, Integer triggerId) {
        try {
            gitlab4jClient.getGitLabApi(userId).getPipelineTriggerApi().deletePipelineTrigger(projectId, triggerId);
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e.getHttpStatus());
        }
    }

    @Override
    public List<PipelineTrigger> listPipelineTrigger(Integer projectId, Integer userId) {
        try {
            return gitlab4jClient.getGitLabApi(userId).getPipelineTriggerApi().listPipelineTrigger(projectId);
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e.getHttpStatus());
        }
    }
}
