package io.choerodon.gitlab.app.service;

import org.gitlab4j.api.models.Project;

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
}
