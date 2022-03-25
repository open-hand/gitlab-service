package io.choerodon.gitlab.app.service;

import java.util.List;

import org.gitlab4j.api.models.PipelineSchedule;
import org.gitlab4j.api.models.Variable;

import io.choerodon.gitlab.infra.dto.AppExternalConfigDTO;

/**
 * 〈功能简述〉
 * 〈〉
 *
 * @author wanghao
 * @since 2022/3/23 15:28
 */
public interface PipelineScheduleService {

    PipelineSchedule create(Integer projectId, Integer userId, AppExternalConfigDTO appExternalConfigDTO, PipelineSchedule pipelineSchedule);


    Variable createVariable(Integer projectId,
                            Integer pipelineScheduleId,
                            Integer userId,
                            AppExternalConfigDTO appExternalConfigDTO, Variable variable);


    PipelineSchedule query(Integer projectId,
                           Integer userId,
                           AppExternalConfigDTO appExternalConfigDTO, Integer pipelineScheduleId);

    List<PipelineSchedule> list(Integer projectId, Integer userId, AppExternalConfigDTO appExternalConfigDTO);

    void update(Integer projectId, Integer userId, AppExternalConfigDTO appExternalConfigDTO, Integer pipelineScheduleId, PipelineSchedule pipelineSchedule);

    void delete(Integer projectId, Integer userId, AppExternalConfigDTO appExternalConfigDTO, Integer pipelineScheduleId);
}
