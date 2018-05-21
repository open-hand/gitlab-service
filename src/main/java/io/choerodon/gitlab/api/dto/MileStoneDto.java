package io.choerodon.gitlab.api.dto;

import java.util.Date;

import org.gitlab4j.api.Constants;

/**
 * Created by zzy on 2018/3/21.
 */
public class MileStoneDto {

    private Integer projectId;
    private Integer milestoneId;
    private String title;
    private String description;
    private String search;
    private Date dueDate;
    private Date startDate;
    private Constants.MilestoneState milestoneState;

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getMilestoneId() {
        return milestoneId;
    }

    public void setMilestoneId(Integer milestoneId) {
        this.milestoneId = milestoneId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Constants.MilestoneState getMilestoneState() {
        return milestoneState;
    }

    public void setMilestoneState(Constants.MilestoneState milestoneState) {
        this.milestoneState = milestoneState;
    }
}
