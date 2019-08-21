package io.choerodon.gitlab.api.controller.v1;

import io.choerodon.core.exception.CommonException;
import io.choerodon.gitlab.app.service.ConfigService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author: trump
 * @date: 2019/8/21 14:25
 * @description:
 */
@RestController
@RequestMapping(value = "/v1/confings")
public class ConfigController {

    @Autowired
    private ConfigService configService;

    /**
     * 获取配置文件gitlab组件admin的token
     *
     * @return token
     */
    @ApiOperation(value = "获取配置文件gitlab组件admin的token")
    @GetMapping("/get_admin_token")
    public ResponseEntity<String> getAdminToken() {
        return Optional.ofNullable(configService.getAdminToken())
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.admin.token.get"));
    }

}
