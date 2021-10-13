package io.choerodon.gitlab.app.service;

import io.choerodon.gitlab.api.vo.PipelineVO;
import io.choerodon.gitlab.infra.dto.AppExternalConfigDTO;
import org.gitlab4j.api.models.Pipeline;

import java.util.List;


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
     * @param projectId            项目 Id
     * @param pipelineId           流水线 Id
     * @param userId               用户Id
     * @param appExternalConfigDTO
     * @return Pipeline
     */
    PipelineVO queryPipeline(Integer projectId, Integer pipelineId, Integer userId, AppExternalConfigDTO appExternalConfigDTO);

    /**
     * Retry jobs in a pipeline
     *
     * @param projectId            项目 Id
     * @param pipelineId           流水线 Id
     * @param userId               用户Id
     * @param appExternalConfigDTO
     * @return
     */
    Pipeline retryPipeline(Integer projectId, Integer pipelineId, Integer userId, AppExternalConfigDTO appExternalConfigDTO);

    /**
     * Cancel a pipelines jobs
     *
     * @param projectId            项目 Id
     * @param pipelineId           流水线 Id
     * @param userId               用户Id
     * @param appExternalConfigDTO
     * @return
     */
    Pipeline cancelPipeline(Integer projectId, Integer pipelineId, Integer userId, AppExternalConfigDTO appExternalConfigDTO);

    /**
     * Create a new pipeline
     *
     * @param projectId
     * @param userId
     * @param ref
     * @param appExternalConfigDTO
     * @return
     */
    Pipeline createPipeline(Integer projectId, Integer userId, String ref, AppExternalConfigDTO appExternalConfigDTO);
}
