package io.choerodon.gitlab.app.service;

import java.util.List;
import java.util.Map;

import org.gitlab4j.api.models.Project;
import org.gitlab4j.api.models.Variable;

import io.choerodon.gitlab.api.vo.VariableVO;
import io.choerodon.gitlab.infra.dto.AppExternalConfigDTO;

/**
 * 〈功能简述〉
 * 〈〉
 *
 * @author wanghao
 * @since 2021/9/28 21:34
 */
public interface ExternalProjectService {

    Project queryExternalProjectByCode(String namespaceCode, String projectCode, AppExternalConfigDTO appExternalConfigDTO);

    List<Variable> getProjectVariable(Integer projectId, AppExternalConfigDTO appExternalConfigDTO);

    List<Map<String, Object>> batchCreateVariable(Integer projectId, List<VariableVO> list, AppExternalConfigDTO appExternalConfigDTO);

}
