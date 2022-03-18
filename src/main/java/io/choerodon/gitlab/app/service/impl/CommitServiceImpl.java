package io.choerodon.gitlab.app.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Commit;
import org.gitlab4j.api.models.CommitPayload;
import org.gitlab4j.api.models.CommitStatuse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.choerodon.core.exception.CommonException;
import io.choerodon.core.exception.FeignException;
import io.choerodon.gitlab.api.vo.CommitStatuseVO;
import io.choerodon.gitlab.app.service.CommitService;
import io.choerodon.gitlab.infra.common.client.Gitlab4jClient;
import io.choerodon.gitlab.infra.dto.AppExternalConfigDTO;
import io.choerodon.gitlab.infra.util.ExternalGitlabApiUtil;

/**
 * Created by zzy on 2018/1/14.
 */
@Service
public class CommitServiceImpl implements CommitService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommitServiceImpl.class);
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private Gitlab4jClient gitlab4jclient;

    @Override
    public Commit getCommit(Integer projectId, String sha, Integer userId) {
        try {
            return gitlab4jclient.getGitLabApi(userId).getCommitsApi().getCommit(projectId, URLEncoder.encode(sha, "UTF-8"));
        } catch (GitLabApiException | UnsupportedEncodingException e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public List<CommitStatuseVO> getCommitStatuse(Integer projectId, String sha, Integer userId) {
        try {
            List<CommitStatuse> commitStatuses = gitlab4jclient.getGitLabApi(userId).getCommitsApi().getCommitStatus(projectId, sha);
            List<CommitStatuseVO> commmitStatuseDTOS = new ArrayList<>();
            commitStatuses.stream().forEach(commitStatuse -> {
                CommitStatuseVO commitStatuseVO = new CommitStatuseVO();
                BeanUtils.copyProperties(commitStatuse, commitStatuseVO);
                if (commitStatuse.getCreated_at() != null && commitStatuse.getStarted_at() != null) {
                    commitStatuseVO.setCreated_at(formatter.format(commitStatuse.getCreated_at()));
                    commitStatuseVO.setStarted_at(formatter.format(commitStatuse.getStarted_at()));
                }
                commmitStatuseDTOS.add(commitStatuseVO);
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
    public List<Commit> getCommitsByRef(Integer gitLabProjectId, String ref, String path) {
        try {
            return gitlab4jclient.getGitLabApi()
                    .getCommitsApi().getCommits(gitLabProjectId, ref, path);
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

    @Override
    public void createCommit(Integer projectId, Integer userId, CommitPayload commitPayload) {
        try {
            gitlab4jclient.getGitLabApi(userId).getCommitsApi().createCommit(projectId, commitPayload);
        } catch (GitLabApiException e) {
            LOGGER.info("error occurred while creating commit. the projectId is {}, the user id is {}, the payload is: {}.", projectId, userId, commitPayload);
            throw new FeignException(e);
        }
    }

    @Override
    public List<Commit> listExternalCommits(Integer projectId, Integer page, Integer size, AppExternalConfigDTO appExternalConfigDTO) {
        GitLabApi gitLabApi;
        if (appExternalConfigDTO == null || appExternalConfigDTO.getGitlabUrl() == null) {
            gitLabApi = gitlab4jclient.getGitLabApi();
        } else {
            gitLabApi = ExternalGitlabApiUtil.createGitLabApi(appExternalConfigDTO);
        }

        try {
            return gitLabApi.getCommitsApi().getCommits(projectId, page, size);
        } catch (GitLabApiException e) {
            throw new CommonException("query.repository.file.failed", e);
        }
    }
}
