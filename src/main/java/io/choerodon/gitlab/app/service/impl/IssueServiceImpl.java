package io.choerodon.gitlab.app.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.IssuesApi;
import org.gitlab4j.api.models.Issue;
import org.springframework.stereotype.Service;

import io.choerodon.core.exception.FeignException;
import io.choerodon.gitlab.api.vo.IssueVO;
import io.choerodon.gitlab.app.service.IssueService;
import io.choerodon.gitlab.infra.common.client.Gitlab4jClient;

@Service
public class IssueServiceImpl implements IssueService {

    private Gitlab4jClient gitlab4jclient;

    public IssueServiceImpl(Gitlab4jClient gitlab4jclient) {
        this.gitlab4jclient = gitlab4jclient;
    }

    @Override
    public Issue createIssue(IssueVO issueVO) {
        try {
            return gitlab4jclient
                    .getGitLabApi(null)
                    .getIssuesApi()
                    .createIssue(issueVO.getProjectId(),
                            issueVO.getTitle(),
                            issueVO.getDescription(),
                            issueVO.getConfidential(),
                            issueVO.getAssigneeIds(),
                            issueVO.getMilestoneId(),
                            issueVO.getLabels(),
                            issueVO.getCreatedAt(),
                            issueVO.getDueDate(),
                            issueVO.getMergeRequestToResolveId(),
                            issueVO.getDiscussionToResolveId());

        } catch (Exception e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public Map<Long, Issue> batchCreateIssue(Map<Long, IssueVO> issueDtos, Integer userId) {
        Map<Long, Issue> map = new HashMap<>();
        for (Object o : issueDtos.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            Long id = Long.valueOf(String.valueOf(entry.getKey()));
            IssueVO issueVO = issueDtos.get(id);
            try {
                Issue issue = gitlab4jclient
                        .getGitLabApi(userId)
                        .getIssuesApi()
                        .createIssue(issueVO.getProjectId(),
                                issueVO.getTitle(),
                                issueVO.getDescription(),
                                issueVO.getConfidential(),
                                issueVO.getAssigneeIds(),
                                issueVO.getMilestoneId(),
                                issueVO.getLabels(),
                                issueVO.getCreatedAt(),
                                issueVO.getDueDate(),
                                issueVO.getMergeRequestToResolveId(),
                                issueVO.getDiscussionToResolveId());
                map.put(id, issue);
            } catch (GitLabApiException e) {
                throw new FeignException(e.getMessage(), e);
            }
        }
        return map;
    }

    @Override
    public Issue updateIssue(IssueVO issueVO) {
        try {
            return gitlab4jclient
                    .getGitLabApi(null)
                    .getIssuesApi()
                    .updateIssue(issueVO.getProjectId(),
                            issueVO.getIssueIid(),
                            issueVO.getTitle(),
                            issueVO.getDescription(),
                            issueVO.getConfidential(),
                            issueVO.getAssigneeIds(),
                            issueVO.getMilestoneId(),
                            issueVO.getLabels(),
                            issueVO.getStateEvent(),
                            issueVO.getUpdateAt(),
                            issueVO.getDueDate());

        } catch (Exception e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public void batchUpdateIssue(Integer userId, List<IssueVO> issueVOS) {
        try {
            GitLabApi gitLabApi = gitlab4jclient.getGitLabApi(userId);
            for (IssueVO issueVO : issueVOS) {
                gitLabApi.getIssuesApi().updateIssue(issueVO.getProjectId(),
                        issueVO.getIssueIid(),
                        issueVO.getTitle(),
                        issueVO.getDescription(),
                        issueVO.getConfidential(),
                        issueVO.getAssigneeIds(),
                        issueVO.getMilestoneId(),
                        issueVO.getLabels(),
                        issueVO.getStateEvent(),
                        issueVO.getUpdateAt(),
                        issueVO.getDueDate());
            }
        } catch (Exception e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public void closeIssue(Integer projectId, Integer issueIid) {
        try {
            gitlab4jclient
                    .getGitLabApi()
                    .getIssuesApi()
                    .closeIssue(projectId, issueIid);
        } catch (Exception e) {
            throw new FeignException(e.getMessage(), e);

        }
    }

    @Override
    public void batchCloseIssue(Integer projectId, List<Integer> issueIds, Integer userId) {
        issueIds.stream().forEach(issueId -> {
            try {
                gitlab4jclient
                        .getGitLabApi(userId)
                        .getIssuesApi()
                        .closeIssue(projectId, issueId);
            } catch (Exception e) {
                throw new FeignException(e.getMessage(), e);
            }
        });
    }

    @Override
    public void openIssue(Integer projectId, Integer issueIid) {
        try {
            gitlab4jclient
                    .getGitLabApi()
                    .getIssuesApi()
                    .openIssue(projectId, issueIid);
        } catch (Exception e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public void batchOpenIssue(Integer projectId, List<Integer> issueIds, Integer userId) {
        issueIds.stream().forEach(issueId -> {
            try {
                gitlab4jclient
                        .getGitLabApi(userId)
                        .getIssuesApi()
                        .openIssue(projectId, issueId);
            } catch (Exception e) {
                throw new FeignException(e.getMessage(), e);
            }
        });
    }

    @Override
    public void deleteIssue(Integer projectId, Integer issueIid) {
        try {
            gitlab4jclient
                    .getGitLabApi()
                    .getIssuesApi()
                    .deleteIssue(projectId, issueIid);
        } catch (Exception e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public void listDeleteIssue(Integer projectId, Integer userId, List<Integer> issueList) {
        try {
            GitLabApi gitLabApi = gitlab4jclient.getGitLabApi(userId);
            for (Integer issue : issueList) {
                gitLabApi.getIssuesApi().deleteIssue(projectId, issue);
            }
        } catch (Exception e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public List<Issue> listIssues(Integer projectId, Integer page, Integer pageSize) {
        try {
            IssuesApi issuesApi = gitlab4jclient
                    .getGitLabApi(null)
                    .getIssuesApi();
            Boolean nonPaged = page == null || pageSize == null;
            if (projectId == null) {
                return nonPaged ? issuesApi.getIssues() : issuesApi.getIssues(page.intValue(), pageSize.intValue());
            } else {
                return nonPaged ? issuesApi.getIssues(projectId) : issuesApi.getIssues(projectId, page, pageSize);
            }
        } catch (Exception e) {
            throw new FeignException(e.getMessage(), e);

        }
    }

    @Override
    public Issue getIssue(Integer projectId, Integer issueIid) {
        try {
            return gitlab4jclient
                    .getGitLabApi(null)
                    .getIssuesApi()
                    .getIssue(projectId, issueIid);
        } catch (Exception e) {
            throw new FeignException(e.getMessage(), e);

        }
    }
}
