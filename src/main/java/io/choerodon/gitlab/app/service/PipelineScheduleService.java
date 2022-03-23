package io.choerodon.gitlab.app.service;

import org.gitlab4j.api.models.PipelineSchedule;

/**
 * 〈功能简述〉
 * 〈〉
 *
 * @author wanghao
 * @since 2022/3/23 15:28
 */
public interface PipelineScheduleService {

    PipelineSchedule create(Integer projectId, Integer userId, PipelineSchedule pipelineSchedule);
}
