package io.choerodon.gitlab.app.service.impl;

import java.util.List;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Branch;
import org.gitlab4j.api.models.Project;
import org.gitlab4j.api.models.RepositoryFile;
import org.gitlab4j.api.models.Tag;
import org.springframework.stereotype.Service;

import io.choerodon.core.exception.CommonException;
import io.choerodon.gitlab.app.service.RepositoryService;
import io.choerodon.gitlab.infra.common.client.Gitlab4jClient;


@Service
public class RepositoryServiceImpl implements RepositoryService {

    private static final String README = "README.md";
    private static final String README_CONTENT =
            "# To customize a template\n"
            + "you need to push the template code to this git repository.\n"
            + "\n"
            + "Please make sure the following file exists.\n"
            + "+ **gitlab-ci.yml**. (Refer to [GitLab Documentation](https://docs.gitlab.com/ee/ci/yaml/))\n"
            + "+ **Dockerfile**. (Refer to [Dockerfile reference](https://docs.docker.com/engine/reference/builder/))\n"
            + "+ **Chart** setting directory. (Refer to [helm](https://github.com/kubernetes/helm))\n"
            + "\n"
            + "Finally, removing or re-editing this **README.md** file to make it useful.";

    private Gitlab4jClient gitlab4jclient;

    public RepositoryServiceImpl(Gitlab4jClient gitlab4jclient) {
        this.gitlab4jclient = gitlab4jclient;
    }

    @Override
    public Branch createBranch(Integer projectId, String branchName, String source) {
        try {
            return this.gitlab4jclient.getGitLabApi(null).getRepositoryApi()
                    .createBranch(projectId, branchName, source);
        } catch (GitLabApiException e) {
            if (e.getMessage().equals("Branch already exists")) {
                Branch branch = new Branch();
                branch.setName("create branch message:Branch already exists");
                return branch;
            }
            throw new CommonException("error.branch.insert");
        }
    }

    @Override
    public List<Tag> listTags(Integer projectId, String username) {
        username = gitlab4jclient.getRealUsername(username);
        try {
            return gitlab4jclient.getGitLabApi(username).getRepositoryApi()
                    .getTags(projectId);
        } catch (GitLabApiException e) {
            throw new CommonException("error.tag.get");
        }
    }

    @Override
    public List<Tag> listTagsByPage(Integer projectId, int page, int perPage, String username) {
        try {
            return gitlab4jclient.getGitLabApi(username)
                    .getRepositoryApi()
                    .getTags(projectId, page, perPage);
        } catch (GitLabApiException e) {
            throw new CommonException("error.tag.getPage");
        }
    }

    @Override
    public Tag createTag(Integer projectId, String tagName, String ref, String username) {
        try {
            return gitlab4jclient.getGitLabApi(username)
                    .getRepositoryApi()
                    .createTag(projectId, tagName, ref, "", "");
        } catch (GitLabApiException e) {
            throw new CommonException("error.tag.create");
        }
    }

    @Override
    public void deleteBranch(Integer projectId, String branchName, String username) {
        try {
            gitlab4jclient
                    .getGitLabApi(username)
                    .getRepositoryApi()
                    .deleteBranch(projectId, branchName);
        } catch (GitLabApiException e) {
            throw new CommonException("error.branch.delete");
        }
    }

    @Override
    public Branch queryBranchByName(Integer projectId, String branchName) {
        try {
            return gitlab4jclient.getGitLabApi(null)
                    .getRepositoryApi()
                    .getBranch(projectId, branchName);
        } catch (GitLabApiException e) {
            return new Branch();
        }
    }

    @Override
    public List<Branch> listBranches(Integer projectId) {
        try {
            return gitlab4jclient.getGitLabApi(null)
                    .getRepositoryApi()
                    .getBranches(projectId);
        } catch (GitLabApiException e) {
            throw new CommonException("error.branch.get");
        }
    }

    @Override
    public boolean createFile(Integer projectId, String userName) {
        GitLabApi gitLabApi = gitlab4jclient.getGitLabApi(userName);
        try {
            Project project = gitLabApi.getProjectApi().getProject(projectId);
            RepositoryFile repositoryFile = new RepositoryFile();
            repositoryFile.setContent(README_CONTENT);
            repositoryFile.setFilePath(README);
            gitLabApi.getRepositoryFileApi().createFile(
                    repositoryFile, project.getId(), "master", "ADD README.md");
        } catch (GitLabApiException e) {
            throw new CommonException("error.file.create");
        }
        return true;
    }

}
