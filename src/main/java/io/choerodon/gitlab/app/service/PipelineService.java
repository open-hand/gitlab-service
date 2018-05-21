package io.choerodon.gitlab.app.service;

import java.util.List;

import org.gitlab4j.api.models.Pipeline;

import io.choerodon.gitlab.api.dto.PipelineDto;


/**
 * Created by zzy on 2018/1/9.
 */
public interface PipelineService {

    /**
     * 分页查询项目下的pipelines
     *
     * @param projectId 项目 Id
     * @param page      页码
     * @param size      每页大小
     * @return List
     */
    List<Pipeline> listPipelinesByPage(Integer projectId, Integer page, Integer size);

    /**
     * 查询项目下的pipelines
     *
     * @param projectId 项目 Id
     * @return List
     */
    List<Pipeline> listPipelines(Integer projectId);

    /**
     * 查询某个pipelines的具体信息
     *
     * @param projectId  项目 Id
     * @param pipelineId 流水线 Id
     * @param userName   用户名
     * @return Pipeline
     */
    PipelineDto queryPipeline(Integer projectId, Integer pipelineId, String userName);

    /**
     * Retry jobs in a pipeline
     *
     * @param projectId  项目 Id
     * @param pipelineId 流水线 Id
     * @param userName   用户名
     * @return
     */
    Pipeline retryPipeline(Integer projectId, Integer pipelineId, String userName);

    /**
     * Cancel a pipelines jobs
     *
     * @param projectId  项目 Id
     * @param pipelineId 流水线 Id
     * @param userName   用户名
     * @return
     */
    Pipeline cancelPipeline(Integer projectId, Integer pipelineId, String userName);
}
