package io.choerodon.gitlab.app.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.choerodon.gitlab.app.service.ConfigService;

/**
 * @author: trump
 * @date: 2019/8/21 16:38
 * @description:
 */
@Service
public class ConfigServiceImpl implements ConfigService {
    @Value("${gitlab.privateToken}")
    private String privateToken;

    @Override
    public String getAdminToken() {
        return privateToken;
    }
}
