package io.choerodon.gitlab.app.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
    public Branch createBranch(Integer projectId, String branchName, String source, Integer userId) {
        try {
            return this.gitlab4jclient.getGitLabApi(userId).getRepositoryApi()
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
    public List<Tag> listTags(Integer projectId, Integer userId) {
        try {
            return gitlab4jclient.getGitLabApi(userId).getRepositoryApi().getTags(projectId);
        } catch (GitLabApiException e) {
            throw new CommonException("error.tag.get");
        }
    }

    @Override
    public List<Tag> listTagsByPage(Integer projectId, int page, int perPage, Integer userId) {
        try {
            return gitlab4jclient.getGitLabApi(userId)
                    .getRepositoryApi()
                    .getTags(projectId, page, perPage);
        } catch (GitLabApiException e) {
            throw new CommonException("error.tag.getPage");
        }
    }

    @Override
    public Tag createTag(Integer projectId, String tagName, String ref, Integer userId) {
        try {
            return gitlab4jclient.getGitLabApi(userId)
                    .getRepositoryApi()
                    .createTag(projectId, tagName, ref, "", "");
        } catch (GitLabApiException e) {
            throw new CommonException("error.tag.create");
        }
    }

    @Override
    public void deleteTag(Integer projectId, String tagName, Integer userId) {
        try {
            gitlab4jclient
                    .getGitLabApi(userId)
                    .getRepositoryApi()
                    .deleteTag(projectId,tagName);
        } catch (GitLabApiException e) {
            throw new CommonException("error.tag.delete");
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
    public List<Branch> listBranches(Integer projectId, Integer userId) {
        try {
            return gitlab4jclient.getGitLabApi(userId)
                    .getRepositoryApi()
                    .getBranches(projectId);
        } catch (GitLabApiException e) {
            throw new CommonException("error.branch.get");
        }
    }

    @Override
    public boolean createFile(Integer projectId, Integer userId) {
        GitLabApi gitLabApi = gitlab4jclient.getGitLabApi(userId);
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

    @Override
    public String getFileReadme(Integer projectId, String commit, String filePath) {
        GitLabApi gitLabApi = gitlab4jclient.getGitLabApi();
        StringBuilder fileContent = new StringBuilder();
        try {
            File file = gitLabApi.getRepositoryFileApi().getRawFile(projectId, commit, filePath, null);
            try (FileReader fileReader = new FileReader(file)) {
                try (BufferedReader reader = new BufferedReader(fileReader)) {
                    String lineTxt;
                    while ((lineTxt = reader.readLine()) != null) {
                        fileContent.append(lineTxt).append("\n");
                    }
                }
            }
        } catch (GitLabApiException e) {
            throw new CommonException("error.file.get");
        } catch (IOException e) {
            throw new CommonException("error.file.read");
        }
        return fileContent.toString();
    }

}
