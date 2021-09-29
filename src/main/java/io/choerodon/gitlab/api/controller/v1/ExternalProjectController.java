package io.choerodon.gitlab.api.controller.v1;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.gitlab4j.api.models.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.choerodon.core.exception.FeignException;
import io.choerodon.gitlab.api.vo.VariableVO;
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
    public ResponseEntity<Project> queryExternalProjectByCode(
            @RequestParam(value = "namespace_code") String namespaceCode,
            @RequestParam(value = "project_code") String projectCode,
            @ApiParam(value = "认证信息", required = true)
            AppExternalConfigDTO appExternalConfigDTO) {
        return ResponseEntity.ok(externalProjectService.queryExternalProjectByCode(namespaceCode, projectCode, appExternalConfigDTO));
    }

    /**
     * 批量增加/更新项目ci环境变量
     *
     * @param projectId 项目Id
     * @param list      variable信息
     * @return Map
     */
    @ApiOperation(value = " 批量增加/更新项目ci环境变量")
    @PutMapping(value = "/{projectId}/variables")
    public ResponseEntity<List<Map<String, Object>>> batchSaveExternalProjectVariable(
            @ApiParam(value = "项目ID", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "variable信息", required = true)
            @RequestBody @Valid List<VariableVO> list,
            AppExternalConfigDTO appExternalConfigDTO) {
        return Optional.ofNullable(externalProjectService.batchCreateVariable(projectId, list, appExternalConfigDTO))
                .map(target -> new ResponseEntity<>(target, HttpStatus.CREATED))
                .orElseThrow(() -> new FeignException("error.projects.variable.batch.create"));
    }
}
