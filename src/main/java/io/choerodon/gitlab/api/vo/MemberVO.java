package io.choerodon.gitlab.api.vo;

import javax.validation.constraints.NotNull;

public class MemberVO {
    @NotNull
    private Integer id;
    @NotNull
    private Integer accessLevel;
    private String expiresAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
