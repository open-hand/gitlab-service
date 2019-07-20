package io.choerodon.gitlab.app.service.impl;

import java.util.UUID;

import io.choerodon.core.exception.FeignException;
import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.RepositoryFileApi;
import org.gitlab4j.api.models.RepositoryFile;

/**
 * Created by Sheep on 2019/7/19.
 */
public class test {

    public static void main(String[] args) throws GitLabApiException {

        GitLabApi gitLabApi = new GitLabApi("http://git.staging.saas.hand-china.com/","kEXZHti27y8zJDyWahaC");

        gitLabApi.setSudoAsId(18397);
        RepositoryFile repositoryFile = new RepositoryFile();
        try {
            repositoryFile.setContent("a new commit.");
            repositoryFile.setFilePath("newFile" + UUID.randomUUID().toString().replaceAll("-", ""));
            repositoryFile = gitLabApi.getRepositoryFileApi().createFile(
                    repositoryFile, 2751,  "feature-SP-11", "ADD FILE");
        } catch (GitLabApiException e) {
            System.out.println(e.getHttpStatus());
            throw new FeignException(e);
        }


    }
}
