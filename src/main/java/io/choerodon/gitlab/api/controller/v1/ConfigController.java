package io.choerodon.gitlab.api.controller.v1;

import io.choerodon.core.exception.CommonException;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
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
    @Value("${gitlab.privateToken}")
    private String privateToken;

    /**
     * 获取配置文件gitlab组件admin的token
     *
     * @return
     */
    @ApiOperation(value = "获取配置文件gitlab组件admin的token")
    @GetMapping("/getAdminToken")
    public ResponseEntity<String> getAdminToken() {
        return Optional.ofNullable(privateToken)
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.admin.token.get"));
    }

}
