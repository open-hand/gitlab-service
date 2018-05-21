package io.choerodon.gitlab.api.dto;

import javax.validation.constraints.NotNull;

public class MemberDto {
    @NotNull
    private Integer userId;
    @NotNull
    private Integer accessLevel;
    private String expiresAt;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(Integer accessLevel) {
        this.accessLevel = accessLevel;
    }

    public String getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(String expiresAt) {
        this.expiresAt = expiresAt;
    }
}
