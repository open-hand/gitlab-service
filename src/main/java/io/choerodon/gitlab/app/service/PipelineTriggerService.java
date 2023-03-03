package io.choerodon.gitlab.app.service;

import java.util.List;

import org.gitlab4j.api.models.PipelineTrigger;

public interface PipelineTriggerService {
    PipelineTrigger createPipelineTrigger(Integer projectId, Integer userId, String description);

    void deletePipelineTrigger(Integer projectId, Integer userId, Integer triggerId);

    List<PipelineTrigger> listPipelineTrigger(Integer projectId, Integer userId);
}
