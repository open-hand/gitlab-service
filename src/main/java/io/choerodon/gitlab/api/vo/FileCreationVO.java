package io.choerodon.gitlab.api.vo;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author zmf
 * @since 19-12-21
 */
public class FileCreationVO {
    @NotNull(message = "error.file.path.null")
    @ApiModelProperty("文件路径")
    private String path;

    @NotNull(message = "error.file.content.null")
    @ApiModelProperty("文件内容")
    private String content;

    @NotNull(message = "error.commit.message.null")
    @ApiModelProperty("提交信息")
    private String commitMessage;

    @NotNull(message = "error.commit.user.id.null")
    @ApiModelProperty("操作人的gitlab用户id")
    private Integer userId;

    @ApiModelProperty("提交到的分支名称")
    private String branchName;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCommitMessage() {
        return commitMessage;
    }

    public void setCommitMessage(String commitMessage) {
        this.commitMessage = commitMessage;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }
}
