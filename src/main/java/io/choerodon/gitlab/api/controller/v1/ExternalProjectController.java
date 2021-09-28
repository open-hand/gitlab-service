package io.choerodon.gitlab.api.controller.v1;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.gitlab4j.api.models.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.choerodon.gitlab.app.service.ExternalProjectService;
import io.choerodon.gitlab.infra.dto.AppExternalConfigDTO;

/**
 * 〈功能简述〉
 * 〈〉
 *
 * @author wanghao
 * @since 2021/9/28 21:33
 */
@RestController
@RequestMapping("/v1/external_projects")
public class ExternalProjectController {

    @Autowired
    private ExternalProjectService externalProjectService;


    @ApiOperation(value = "通过项目id查询项目")
    @GetMapping(value = "/query_by_code")
    public ResponseEntity<Project> queryProjectByCode(
            @RequestParam(value = "namespace_code") String namespaceCode,
            @RequestParam(value = "project_code") String projectCode,
            @ApiParam(value = "认证信息", required = true)
            AppExternalConfigDTO appExternalConfigDTO) {
        return ResponseEntity.ok(externalProjectService.queryProjectByCode(namespaceCode, projectCode, appExternalConfigDTO));
    }
}
