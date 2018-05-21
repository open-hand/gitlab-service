package io.choerodon.gitlab.api.dto;

import java.util.Date;

import org.gitlab4j.api.models.PipelineStatus;
import org.gitlab4j.api.models.User;

/**
 * Created by Zenger on 2018/1/10.
 */
public class PipelineDto {

    private Integer id;
    private PipelineStatus status;
    private String ref;
    private String sha;
    private String beforeSha;
    private Boolean tag;
    private String yamlErrors;
    private User user;
    private String createdAt;
    private Date updated_at;
    private Date started_at;
    private Date finished_at;
    private Date committed_at;
    private String coverage;
    private Integer duration;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PipelineStatus getStatus() {
        return status;
    }

    public void setStatus(PipelineStatus status) {
        this.status = status;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public String getBeforeSha() {
        return beforeSha;
    }

    public void setBeforeSha(String beforeSha) {
        this.beforeSha = beforeSha;
    }

    public Boolean getTag() {
        return tag;
    }

    public void setTag(Boolean tag) {
        this.tag = tag;
    }

    public String getYamlErrors() {
        return yamlErrors;
    }

    public void setYamlErrors(String yamlErrors) {
        this.yamlErrors = yamlErrors;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updated_at;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updated_at = updatedAt;
    }

    public Date getStartedAt() {
        return started_at;
    }

    public void setStartedAt(Date startedAt) {
        this.started_at = startedAt;
    }

    public Date getFinishedAt() {
        return finished_at;
    }

    public void setFinishedAt(Date finishedAt) {
        this.finished_at = finishedAt;
    }

    public Date getCommittedAt() {
        return committed_at;
    }

    public void setCommittedAt(Date committedAt) {
        this.committed_at = committedAt;
    }

    public String getCoverage() {
        return coverage;
    }

    public void setCoverage(String coverage) {
        this.coverage = coverage;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
