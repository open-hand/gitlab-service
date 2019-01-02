package io.choerodon.gitlab.api.controller.v1;

import io.choerodon.gitlab.app.service.UrlValidationService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zmf
 */
@RestController
@RequestMapping(value = "/v1/url_validation")
public class UrlValidationController {
    @Autowired
    private UrlValidationService urlValidationService;

    /**
     * 验证用于克隆仓库的url及授权的access token是否有效
     *
     * @param url         用于克隆仓库的url(http/https)
     * @param accessToken gitlab授权的access token
     * @return true 如果有效
     */
    @ApiOperation("验证用于克隆仓库的url及授权的access token是否有效")
    @GetMapping
    public ResponseEntity<Boolean> validateUrlAndAccessToken(
            @ApiParam("clone仓库的地址")
            @RequestParam("url") String url,
            @ApiParam("gitlab access token")
            @RequestParam("access_token") String accessToken) {
        return new ResponseEntity<>(urlValidationService.validateUrlAndAccessToken(url, accessToken), HttpStatus.OK);
    }
}
