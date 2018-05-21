package io.choerodon.gitlab.app.service;

import java.util.Date;
import java.util.List;

import org.gitlab4j.api.models.Note;


public interface NotesService {

    /**
     * 查询Note列表
     *
     * @param projectId 项目id
     * @param issueIid  issue Id
     * @param page      页码
     * @param size      每页大小
     * @return List
     */
    List<Note> listIssueNotes(Integer projectId, Integer issueIid, int page, int size);

    /**
     * 查询单个Note信息
     *
     * @param projectId 项目id
     * @param issueIid  Issue Id
     * @param noteId    标记 Id
     * @return Note
     */
    Note queryIssueNote(Integer projectId, Integer issueIid, Integer noteId);

    /**
     * 创建Note
     *
     * @param projectId 项目id
     * @param issueIid  Issue Id
     * @param body      标记内容
     * @param createdAt 创建时间
     * @return Note
     */
    Note createIssueNote(Integer projectId, Integer issueIid, String body, Date createdAt);

    /**
     * 更新Note
     *
     * @param projectId 项目id
     * @param issueIid  Issue Id
     * @param noteId    标记 Id
     * @param body      标记内容
     * @return Note
     */
    Note updateIssueNote(Integer projectId, Integer issueIid, Integer noteId, String body);

    /**
     * 删除Note
     *
     * @param projectId 项目id
     * @param issueIid  Issue Id
     * @param noteId    标记 id
     */
    void deleteIssueNote(Integer projectId, Integer issueIid, Integer noteId);
}
