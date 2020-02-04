package io.choerodon.gitlab.api.vo;

import io.swagger.annotations.ApiModelProperty;
import org.gitlab4j.api.models.User;

/**
 * @author zmf
 * @since 2/3/20
 */
public class UserWithPassword extends User {
    @ApiModelProperty("GitLab密码")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
