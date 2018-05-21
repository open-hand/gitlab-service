package io.choerodon.gitlab.app.service;

import java.util.List;
import java.util.Map;

import org.gitlab4j.api.models.Issue;

import io.choerodon.gitlab.api.dto.IssueDto;

public interface IssueService {

    /**
     * 创建issue
     *
     * @param issueDto issueDto对象
     * @return Issue
     */
    Issue createIssue(IssueDto issueDto);

    /**
     * 批量创建issue
     *
     * @param userName  用户名
     * @param issueDtos issueDto对象Map
     * @return Map
     */
    Map<Long, Issue> batchCreateIssue(Map<Long, IssueDto> issueDtos, String userName);

    /**
     * 更新issue
     *
     * @param issueDto issueDto对象
     * @return Issue
     */
    Issue updateIssue(IssueDto issueDto);

    /**
     * 批量更新issue
     *
     * @param userName  用户名
     * @param issueDtos issueDto对象
     */
    void batchUpdateIssue(String userName, List<IssueDto> issueDtos);

    /**
     * 关闭issue
     *
     * @param projectId 项目id
     * @param issueIid  issueIid
     */
    void closeIssue(Integer projectId, Integer issueIid);

    /**
     * 批量关闭issue
     *
     * @param projectId 项目id
     * @param issueIds  issueIds
     */
    void batchCloseIssue(Integer projectId, List<Integer> issueIds, String userName);

    /**
     * 开启issue
     *
     * @param projectId 项目id
     * @param issueIid  issueIid
     */
    void openIssue(Integer projectId, Integer issueIid);

    /**
     * 批量开启issue
     *
     * @param projectId 项目id
     * @param issueIds  issueIid
     * @param userName  用户名
     */
    void batchOpenIssue(Integer projectId, List<Integer> issueIds, String userName);

    /**
     * 删除issue
     *
     * @param projectId 项目id
     * @param issueIid  issueIid
     */
    void deleteIssue(Integer projectId, Integer issueIid);

    /**
     * 批量删除issue
     *
     * @param projectId 项目id
     * @param userName  用户名
     * @param issueList issueIid List
     */
    void listDeleteIssue(Integer projectId, String userName, List<Integer> issueList);

    /**
     * 查询issues
     *
     * @param projectId 项目id
     * @param page      页码
     * @param pageSize  每页大小
     * @return List
     */
    List<Issue> listIssues(Integer projectId, Integer page, Integer pageSize);

    /**
     * 根据issueIid查询单个issue
     *
     * @param projectId 项目id
     * @param issueIid  issueIid
     * @return Issue
     */
    Issue getIssue(Integer projectId, Integer issueIid);

}
