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
     * @param userId  用户Id
     * @param issueDtos issueDto对象Map
     * @return Map
     */
    Map<Long, Issue> batchCreateIssue(Map<Long, IssueDto> issueDtos, Integer userId);

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
     * @param userId  用户Id
     * @param issueDtos issueDto对象
     */
    void batchUpdateIssue(Integer userId, List<IssueDto> issueDtos);

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
    void batchCloseIssue(Integer projectId, List<Integer> issueIds, Integer userId);

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
     * @param userId  用户Id
     */
    void batchOpenIssue(Integer projectId, List<Integer> issueIds, Integer userId);

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
     * @param userId  用户Id
     * @param issueList issueIid List
     */
    void listDeleteIssue(Integer projectId, Integer userId, List<Integer> issueList);

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
