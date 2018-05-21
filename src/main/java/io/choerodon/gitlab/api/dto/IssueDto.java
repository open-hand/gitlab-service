package io.choerodon.gitlab.api.dto;

import java.util.Date;
import java.util.List;

import org.gitlab4j.api.Constants;


/**
 * Created by zzy on 2018/3/21.
 */
public class IssueDto {
    private Integer projectId;
    private String title;
    private Integer issueIid;
    private String description;
    private Boolean confidential;
    private List<Integer> assigneeIds;
    private Integer milestoneId;
    private String labels;
    private Date createdAt;
    private Date updateAt;
    private Date dueDate;
    private Integer mergeRequestToResolveId;
    private Integer discussionToResolveId;
    private Constants.StateEvent stateEvent;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getIssueIid() {
        return issueIid;
    }

    public void setIssueIid(Integer issueIid) {
        this.issueIid = issueIid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getConfidential() {
        return confidential;
    }

    public void setConfidential(Boolean confidential) {
        this.confidential = confidential;
    }

    public List<Integer> getAssigneeIds() {
        return assigneeIds;
    }

    public void setAssigneeIds(List<Integer> assigneeIds) {
        this.assigneeIds = assigneeIds;
    }

    public Integer getMilestoneId() {
        return milestoneId;
    }

    public void setMilestoneId(Integer milestoneId) {
        this.milestoneId = milestoneId;
    }

    public String getLabels() {
        return labels;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getMergeRequestToResolveId() {
        return mergeRequestToResolveId;
    }

    public void setMergeRequestToResolveId(Integer mergeRequestToResolveId) {
        this.mergeRequestToResolveId = mergeRequestToResolveId;
    }

    public Integer getDiscussionToResolveId() {
        return discussionToResolveId;
    }

    public void setDiscussionToResolveId(Integer discussionToResolveId) {
        this.discussionToResolveId = discussionToResolveId;
    }

    public Constants.StateEvent getStateEvent() {
        return stateEvent;
    }

    public void setStateEvent(Constants.StateEvent stateEvent) {
        this.stateEvent = stateEvent;
    }
}
