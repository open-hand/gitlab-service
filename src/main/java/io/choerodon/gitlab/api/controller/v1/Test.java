package io.choerodon.gitlab.api.controller.v1;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Commit;

import java.util.List;
public class Test {

    public static void main(String arg0[]) {
        GitLabApi newGitLabApi = new GitLabApi("http://gitlab.alpha.saas.hand-china.com", "fnp5gta2vsQ4NKkkd3nq");
        try {
            Commit commit = newGitLabApi.getCommitsApi().getCommit(32,"c6ac8a4404b3a6e7c976651aa0d19ae8611ad413");
            System.out.print("123");
        } catch (GitLabApiException e) {
            e.printStackTrace();
        }

    }
}
