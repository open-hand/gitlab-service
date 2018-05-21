package io.choerodon.gitlab.app.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.IssuesApi;
import org.gitlab4j.api.models.Issue;
import org.springframework.stereotype.Service;

import io.choerodon.core.exception.CommonException;
import io.choerodon.gitlab.api.dto.IssueDto;
import io.choerodon.gitlab.app.service.IssueService;
import io.choerodon.gitlab.infra.common.client.Gitlab4jClient;

@Service
public class IssueServiceImpl implements IssueService {

    private Gitlab4jClient gitlab4jclient;

    public IssueServiceImpl(Gitlab4jClient gitlab4jclient) {
        this.gitlab4jclient = gitlab4jclient;
    }

    @Override
    public Issue createIssue(IssueDto issueDto) {
        try {
            return gitlab4jclient
                    .getGitLabApi(null)
                    .getIssuesApi()
                    .createIssue(issueDto.getProjectId(),
                            issueDto.getTitle(),
                            issueDto.getDescription(),
                            issueDto.getConfidential(),
                            issueDto.getAssigneeIds(),
                            issueDto.getMilestoneId(),
                            issueDto.getLabels(),
                            issueDto.getCreatedAt(),
                            issueDto.getDueDate(),
                            issueDto.getMergeRequestToResolveId(),
                            issueDto.getDiscussionToResolveId());

        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }

    @Override
    public Map<Long, Issue> batchCreateIssue(Map<Long, IssueDto> issueDtos, String userName) {
        Map<Long, Issue> map = new HashMap<>();
        for (Object o : issueDtos.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            Long id = Long.valueOf(String.valueOf(entry.getKey()));
            IssueDto issueDto = issueDtos.get(id);
            try {
                Issue issue = gitlab4jclient
                        .getGitLabApi(userName)
                        .getIssuesApi()
                        .createIssue(issueDto.getProjectId(),
                                issueDto.getTitle(),
                                issueDto.getDescription(),
                                issueDto.getConfidential(),
                                issueDto.getAssigneeIds(),
                                issueDto.getMilestoneId(),
                                issueDto.getLabels(),
                                issueDto.getCreatedAt(),
                                issueDto.getDueDate(),
                                issueDto.getMergeRequestToResolveId(),
                                issueDto.getDiscussionToResolveId());
                map.put(id, issue);
            } catch (GitLabApiException e) {
                throw new CommonException(e.getMessage());
            }
        }
        return map;
    }

    @Override
    public Issue updateIssue(IssueDto issueDto) {
        try {
            return gitlab4jclient
                    .getGitLabApi(null)
                    .getIssuesApi()
                    .updateIssue(issueDto.getProjectId(),
                            issueDto.getIssueIid(),
                            issueDto.getTitle(),
                            issueDto.getDescription(),
                            issueDto.getConfidential(),
                            issueDto.getAssigneeIds(),
                            issueDto.getMilestoneId(),
                            issueDto.getLabels(),
                            issueDto.getStateEvent(),
                            issueDto.getUpdateAt(),
                            issueDto.getDueDate());

        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }

    @Override
    public void batchUpdateIssue(String userName, List<IssueDto> issueDtos) {
        try {
            GitLabApi gitLabApi = gitlab4jclient.getGitLabApi(gitlab4jclient.getRealUsername(userName));
            for (IssueDto issueDto : issueDtos) {
                gitLabApi.getIssuesApi().updateIssue(issueDto.getProjectId(),
                        issueDto.getIssueIid(),
                        issueDto.getTitle(),
                        issueDto.getDescription(),
                        issueDto.getConfidential(),
                        issueDto.getAssigneeIds(),
                        issueDto.getMilestoneId(),
                        issueDto.getLabels(),
                        issueDto.getStateEvent(),
                        issueDto.getUpdateAt(),
                        issueDto.getDueDate());
            }
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }

    @Override
    public void closeIssue(Integer projectId, Integer issueIid) {
        try {
            gitlab4jclient
                    .getGitLabApi(null)
                    .getIssuesApi()
                    .closeIssue(projectId, issueIid);
        } catch (Exception e) {
            throw new CommonException(e.getMessage());

        }
    }

    @Override
    public void batchCloseIssue(Integer projectId, List<Integer> issueIds, String userName) {
        issueIds.stream().forEach(issueId -> {
            try {
                gitlab4jclient
                        .getGitLabApi(gitlab4jclient.getRealUsername(userName))
                        .getIssuesApi()
                        .closeIssue(projectId, issueId);
            } catch (Exception e) {
                throw new CommonException(e.getMessage());
            }
        });
    }

    @Override
    public void openIssue(Integer projectId, Integer issueIid) {
        try {
            gitlab4jclient
                    .getGitLabApi(null)
                    .getIssuesApi()
                    .openIssue(projectId, issueIid);
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }

    @Override
    public void batchOpenIssue(Integer projectId, List<Integer> issueIds, String userName) {
        issueIds.stream().forEach(issueId -> {
            try {
                gitlab4jclient
                        .getGitLabApi(gitlab4jclient.getRealUsername(userName))
                        .getIssuesApi()
                        .openIssue(projectId, issueId);
            } catch (Exception e) {
                throw new CommonException(e.getMessage());
            }
        });
    }

    @Override
    public void deleteIssue(Integer projectId, Integer issueIid) {
        try {
            gitlab4jclient
                    .getGitLabApi(null)
                    .getIssuesApi()
                    .deleteIssue(projectId, issueIid);
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
    }

    @Override
    public void listDeleteIssue(Integer projectId, String userName, List<Integer> issueList) {
        try {
            GitLabApi gitLabApi = gitlab4jclient.getGitLabApi(userName);
            for (Integer issue : issueList) {
                gitLabApi.getIssuesApi().deleteIssue(projectId, issue);
            }
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
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
            throw new CommonException(e.getMessage());

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
            throw new CommonException(e.getMessage());

        }
    }
}
