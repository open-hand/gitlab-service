package io.choerodon.gitlab.app.service.impl;

import java.util.Date;
import java.util.List;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.models.Note;
import org.springframework.stereotype.Service;

import io.choerodon.core.exception.CommonException;
import io.choerodon.gitlab.app.service.NotesService;
import io.choerodon.gitlab.infra.common.client.Gitlab4jClient;


@Service
public class NotesServiceImpl implements NotesService {

    private Gitlab4jClient gitlab4jclient;

    public NotesServiceImpl(Gitlab4jClient gitlab4jclient) {
        this.gitlab4jclient = gitlab4jclient;
    }

    @Override
    public List<Note> listIssueNotes(Integer projectId, Integer issueIid, int page, int size) {
        try {
            return gitlab4jclient.getGitLabApi(null)
                    .getNotesApi().getIssueNotes(projectId, issueIid, page, size);
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage());
        }
    }

    @Override
    public Note queryIssueNote(Integer projectId, Integer issueIid, Integer noteId) {
        try {
            return gitlab4jclient.getGitLabApi(null)
                    .getNotesApi().getIssueNote(projectId, issueIid, noteId);
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage());
        }
    }

    @Override
    public Note createIssueNote(Integer projectId, Integer issueIid, String body, Date createdAt) {
        GitLabApi gitLabApi = gitlab4jclient.getGitLabApi(null);
        try {
            return createdAt == null
                    ? gitLabApi.getNotesApi().createIssueNote(projectId, issueIid, body)
                    : gitLabApi.getNotesApi().createIssueNote(projectId, issueIid, body, createdAt);
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage());
        }
    }

    @Override
    public Note updateIssueNote(Integer projectId, Integer issueIid, Integer noteId, String body) {
        try {
            return gitlab4jclient.getGitLabApi(null)
                    .getNotesApi().updateIssueNote(projectId, issueIid, noteId, body);
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage());
        }
    }

    @Override
    public void deleteIssueNote(Integer projectId, Integer issueIid, Integer noteId) {
        try {
            gitlab4jclient.getGitLabApi(null)
                    .getNotesApi().deleteIssueNote(projectId, issueIid, noteId);
        } catch (Exception e) {
            throw new CommonException("error.note.delete");
        }
    }
}
