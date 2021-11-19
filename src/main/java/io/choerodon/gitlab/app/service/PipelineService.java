package io.choerodon.gitlab.app.service;

import java.util.List;
import java.util.Map;

import org.gitlab4j.api.models.Pipeline;

import io.choerodon.gitlab.api.vo.PipelineVO;


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
     * @param userId    用户Id
     * @return List
     */
    List<Pipeline> listPipelinesByPage(Integer projectId, Integer page, Integer size,Integer userId);

    /**
     * 查询项目下的pipelines
     *
     * @param projectId 项目 Id
     * @param userId    用户Id
     * @return List
     */
    List<Pipeline> listPipelines(Integer projectId,Integer userId);

    /**
     * 查询某个pipelines的具体信息
     *
     * @param projectId  项目 Id
     * @param pipelineId 流水线 Id
     * @param userId   用户Id
     * @return Pipeline
     */
    PipelineVO queryPipeline(Integer projectId, Integer pipelineId, Integer userId);

    /**
     * Retry jobs in a pipeline
     *
     * @param projectId  项目 Id
     * @param pipelineId 流水线 Id
     * @param userId   用户Id
     * @return
     */
    Pipeline retryPipeline(Integer projectId, Integer pipelineId, Integer userId);

    /**
     * Cancel a pipelines jobs
     *
     * @param projectId  项目 Id
     * @param pipelineId 流水线 Id
     * @param userId   用户Id
     * @return
     */
    Pipeline cancelPipeline(Integer projectId, Integer pipelineId, Integer userId);

    /**
     * Create a new pipeline
     *
     * @param projectId
     * @param userId
     * @param ref
     * @param variables
     * @return
     */
    Pipeline createPipeline(Integer projectId, Integer userId, String ref, Map<String, String> variables);
}
