package io.choerodon.gitlab.app.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.choerodon.gitlab.api.dto.CommitDTO;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Commit;
import org.gitlab4j.api.models.CommitStatuse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.choerodon.core.exception.FeignException;
import io.choerodon.gitlab.api.dto.CommitStatuseDTO;
import io.choerodon.gitlab.app.service.CommitService;
import io.choerodon.gitlab.infra.common.client.Gitlab4jClient;

/**
 * Created by zzy on 2018/1/14.
 */
@Service
public class CommitServiceImpl implements CommitService {

    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private Gitlab4jClient gitlab4jclient;

    @Override
    public CommitDTO getCommit(Integer projectId, String sha, Integer userId) {
        try {
            Commit commit = gitlab4jclient.getGitLabApi(userId).getCommitsApi().getCommit(projectId, sha);
            CommitDTO commitDTO = new CommitDTO();
            BeanUtils.copyProperties(commit, commitDTO, "createdAt", "committedDate");
            if (commit.getCreatedAt() != null) {
                commitDTO.setCreatedAt(formatter.format(commit.getCreatedAt()));
            }
            if (commit.getCommittedDate() != null) {
                commitDTO.setCommittedDate(formatter.format(commit.getCommittedDate()));
            }
            return commitDTO;
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public List<CommitStatuseDTO> getCommitStatuse(Integer projectId, String sha, Integer userId) {
        try {
            List<CommitStatuse> commitStatuses = gitlab4jclient.getGitLabApi(userId).getCommitsApi().getCommitStatus(projectId, sha);
            List<CommitStatuseDTO> commmitStatuseDTOS = new ArrayList<>();
            commitStatuses.stream().forEach(commitStatuse -> {
                CommitStatuseDTO commitStatuseDTO = new CommitStatuseDTO();
                BeanUtils.copyProperties(commitStatuse, commitStatuseDTO);
                if (commitStatuse.getCreated_at() != null && commitStatuse.getStarted_at() != null) {
                    commitStatuseDTO.setCreated_at(formatter.format(commitStatuse.getCreated_at()));
                    commitStatuseDTO.setStarted_at(formatter.format(commitStatuse.getStarted_at()));
                }
                commmitStatuseDTOS.add(commitStatuseDTO);
            });
            return commmitStatuseDTOS;

        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public List<Commit> getCommits(Integer gitLabProjectId, String ref, String since) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss zzz");
        Date sinceDate = null;
        try {
            sinceDate = simpleDateFormat.parse(since);
        } catch (ParseException e) {
            throw new FeignException(e.getMessage(), e);
        }
        try {
            return gitlab4jclient.getGitLabApi()
                    .getCommitsApi().getCommits(gitLabProjectId, ref, sinceDate, new Date(), null);
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public List<Commit> listCommits(Integer gilabProjectId, int page, int size, Integer userId) {
        try {
            return gitlab4jclient.getGitLabApi(userId).getCommitsApi().getCommits(gilabProjectId, page, size);
        } catch (GitLabApiException e) {
            throw new FeignException(e);
        }
    }
}
