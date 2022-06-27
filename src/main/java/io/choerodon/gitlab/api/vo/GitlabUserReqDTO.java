package io.choerodon.gitlab.api.vo;

import java.util.Date;

/**
 * User: Mr.Wang
 * Date: 2020/2/18
 */
public class GitlabUserReqDTO {
    //Gitlab的用户名
    private String username;
    //Gitlab的email
    private String email;
    //Gitlab的用户昵称
    private String name;
    //Gitlab用户创建项目的限额
    private Integer projectsLimit;
    //Gitlab用户邮箱确认的时间
    private Date confirmedAt;
    //外部登录提供者
    private String provider;
    //用户Id
    private String externUid;
    //用户是否可以创建组
    private Boolean canCreateGroup;
    private Boolean skipConfirmation;

    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getExternUid() {
        return externUid;
    }

    public void setExternUid(String externUid) {
        this.externUid = externUid;
    }

    public Date getConfirmedAt() {
        return confirmedAt;
    }

    public void setConfirmedAt(Date confirmedAt) {
        this.confirmedAt = confirmedAt;
    }

    public Integer getProjectsLimit() {
        return projectsLimit;
    }

    public void setProjectsLimit(Integer projectsLimit) {
        this.projectsLimit = projectsLimit;
    }

    public Boolean getCanCreateGroup() {
        return canCreateGroup;
    }

    public void setCanCreateGroup(Boolean canCreateGroup) {
        this.canCreateGroup = canCreateGroup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getSkipConfirmation() {
        return skipConfirmation;
    }

    public void setSkipConfirmation(Boolean skipConfirmation) {
        this.skipConfirmation = skipConfirmation;
    }
}
