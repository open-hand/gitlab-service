package io.choerodon.gitlab.app.task;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.models.ApplicationSettings;
import org.gitlab4j.api.models.Setting;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 〈功能简述〉
 * 〈〉
 *
 * @author wanghao
 * @since 2023/5/5 14:53
 */
@Component
public class GitlabConfigCommandRunner implements CommandLineRunner {

    @Value("${gitlab.url}")
    private String url;

    @Value("${gitlab.privateToken}")
    private String privateToken;

    @Override
    public void run(String... args) throws Exception {
        GitLabApi gitLabApi = new GitLabApi(url, privateToken);
        ApplicationSettings applicationSettings = gitLabApi.getApplicationSettingsApi().getApplicationSettings();
        if (!Boolean.TRUE.equals(applicationSettings.getSetting(Setting.ALLOW_LOCAL_REQUESTS_FROM_WEB_HOOKS_AND_SERVICES))) {
            gitLabApi.getApplicationSettingsApi().updateApplicationSetting(Setting.ALLOW_LOCAL_REQUESTS_FROM_WEB_HOOKS_AND_SERVICES, true);
        }
        if (!Boolean.TRUE.equals(applicationSettings.getSetting(Setting.ALLOW_LOCAL_REQUESTS_FROM_SYSTEM_HOOKS))) {
            gitLabApi.getApplicationSettingsApi().updateApplicationSetting(Setting.ALLOW_LOCAL_REQUESTS_FROM_SYSTEM_HOOKS, true);
        }
    }
}
