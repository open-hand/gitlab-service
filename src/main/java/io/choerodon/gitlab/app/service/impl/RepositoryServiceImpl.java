package io.choerodon.gitlab.app.service.impl;

import java.util.List;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Branch;
import org.gitlab4j.api.models.CompareResults;
import org.gitlab4j.api.models.RepositoryFile;
import org.gitlab4j.api.models.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.choerodon.core.exception.FeignException;
import io.choerodon.gitlab.app.service.RepositoryService;
import io.choerodon.gitlab.infra.common.client.Gitlab4jClient;
import org.springframework.util.StringUtils;


@Service
public class RepositoryServiceImpl implements RepositoryService {

    private static final Logger logger = LoggerFactory.getLogger(RepositoryServiceImpl.class);

    private Gitlab4jClient gitlab4jclient;

    public RepositoryServiceImpl(Gitlab4jClient gitlab4jclient) {
        this.gitlab4jclient = gitlab4jclient;
    }

    @Override
    public Branch createBranch(Integer projectId, String branchName, String source, Integer userId) {
        try {
            return this.gitlab4jclient.getGitLabApi().getRepositoryApi()
                    .createBranch(projectId, branchName, source);
        } catch (GitLabApiException e) {
            if (e.getMessage().equals("Branch already exists")) {
                Branch branch = new Branch();
                branch.setName("create branch message:Branch already exists");
                return branch;
            }
            throw new FeignException("error.branch.insert");
        }
    }

    @Override
    public List<Tag> listTags(Integer projectId, Integer userId) {
        try {
            return gitlab4jclient.getGitLabApi(userId).getRepositoryApi().getTags(projectId);
        } catch (GitLabApiException e) {
            throw new FeignException("error.tag.get");
        }
    }

    @Override
    public List<Tag> listTagsByPage(Integer projectId, int page, int perPage, Integer userId) {
        try {
            return gitlab4jclient.getGitLabApi(userId)
                    .getRepositoryApi()
                    .getTags(projectId, page, perPage);
        } catch (GitLabApiException e) {
            throw new FeignException("error.tag.getPage");
        }
    }

    @Override
    public Tag createTag(Integer projectId, String tagName, String ref, String msg, String releaseNotes, Integer userId) {
        try {
            return gitlab4jclient.getGitLabApi(userId)
                    .getRepositoryApi()
                    .createTag(projectId, tagName, ref, msg, releaseNotes);
        } catch (GitLabApiException e) {
            throw new FeignException("error.tag.create: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteTag(Integer projectId, String tagName, Integer userId) {
        try {
            gitlab4jclient
                    .getGitLabApi(userId)
                    .getRepositoryApi()
                    .deleteTag(projectId, tagName);
        } catch (GitLabApiException e) {
            throw new FeignException("error.tag.delete: " + e.getMessage(), e);
        }
    }

    @Override
    public Tag updateTagRelease(Integer projectId, String name, String releaseNotes, Integer userId) {
        try {
            return gitlab4jclient.getGitLabApi(userId)
                    .getRepositoryApi()
                    .updateTagRelease(projectId, name, releaseNotes);
        } catch (GitLabApiException e) {
            throw new FeignException("error.tag.update: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteBranch(Integer projectId, String branchName, Integer userId) {
        try {
            gitlab4jclient
                    .getGitLabApi(userId)
                    .getRepositoryApi()
                    .deleteBranch(projectId, branchName);
        } catch (GitLabApiException e) {
            throw new FeignException("error.branch.delete");
        }
    }

    @Override
    public Branch queryBranchByName(Integer projectId, String branchName) {
        try {
            return gitlab4jclient.getGitLabApi()
                    .getRepositoryApi()
                    .getBranch(projectId, branchName);
        } catch (GitLabApiException e) {
            return new Branch();
        }
    }

    @Override
    public List<Branch> listBranches(Integer projectId, Integer userId) {
        try {
            return gitlab4jclient.getGitLabApi()
                    .getRepositoryApi()
                    .getBranches(projectId);
        } catch (GitLabApiException e) {
            throw new FeignException("error.branch.get",e);
        }
    }

    @Override
    public RepositoryFile getFile(Integer projectId, String commit, String filePath) {
        GitLabApi gitLabApi = gitlab4jclient.getGitLabApi();
        RepositoryFile file;
        try {
            file = gitLabApi.getRepositoryFileApi().getFile(filePath, projectId, commit);
        } catch (GitLabApiException e) {
            return null;
        }
        return file;
    }

    @Override
    public CompareResults getDiffs(Integer projectId, String from, String to) {
        GitLabApi gitLabApi = gitlab4jclient.getGitLabApi();
        try {
            return gitLabApi.getRepositoryApi().compare(projectId, from, to);
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public RepositoryFile createFile(Integer projectId, String path, String content, String commitMessage, Integer userId, String branchName) {
        GitLabApi gitLabApi = gitlab4jclient.getGitLabApi(userId);
        RepositoryFile repositoryFile = new RepositoryFile();
        try {
            repositoryFile.setContent(content);
            repositoryFile.setFilePath(path);
            repositoryFile = gitLabApi.getRepositoryFileApi().createFile(
                    repositoryFile, projectId, StringUtils.isEmpty(branchName) ? "master" : branchName, "ADD FILE");
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(),e);
        }
        return repositoryFile;
    }

    @Override
    public RepositoryFile updateFile(Integer projectId, String path, String content, String commitMessage, Integer userId) {
        GitLabApi gitLabApi = gitlab4jclient.getGitLabApi(userId);
        RepositoryFile repositoryFile = new RepositoryFile();
        try {
            repositoryFile.setContent(content);
            repositoryFile.setFilePath(path);
            repositoryFile = gitLabApi.getRepositoryFileApi().updateFile(repositoryFile, projectId, "master", commitMessage);
        } catch (GitLabApiException e) {
            if (e.getHttpStatus() == 200) {
                return repositoryFile;
            } else {
                throw new FeignException(e.getMessage(),e);
            }
        }
        return repositoryFile;
    }

    @Override
    public void deleteFile(Integer projectId, String path, String commitMessage, Integer userId) {
        GitLabApi gitLabApi = gitlab4jclient.getGitLabApi(userId);
        try {
            gitLabApi.getRepositoryFileApi().deleteFile(path, projectId, "master", "DELETE FILE");
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

}
