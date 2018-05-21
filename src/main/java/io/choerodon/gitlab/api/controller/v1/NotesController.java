package io.choerodon.gitlab.api.controller.v1;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.gitlab4j.api.models.Note;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.choerodon.core.exception.CommonException;
import io.choerodon.gitlab.app.service.NotesService;

@RestController
@RequestMapping("/v1/notes")
public class NotesController {


    private NotesService notesService;

    public NotesController(NotesService notesService) {
        this.notesService = notesService;
    }

    /**
     * 查询Note列表
     *
     * @param projectId 项目id
     * @param issueIid  issue Id
     * @param page      页码
     * @param size      每页大小
     * @return List
     */
    @ApiOperation(value = "查询Note列表")
    @GetMapping
    public ResponseEntity<List<Note>> list(
            @ApiParam(value = "项目Id", required = true)
            @RequestParam Integer projectId,
            @ApiParam(value = "gitlab Issue ID", required = true)
            @RequestParam Integer issueIid,
            @ApiParam(value = "page", required = true)
            @RequestParam int page,
            @ApiParam(value = "size", required = true)
            @RequestParam int size) {
        return Optional.ofNullable(notesService.listIssueNotes(projectId, issueIid, page, size))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.notes.query"));
    }

    /**
     * 查询单个Note信息
     *
     * @param projectId 项目id
     * @param issueIid  Issue Id
     * @param noteId    标记 Id
     * @return Note
     */
    @ApiOperation(value = "查询Note")
    @GetMapping(value = "/{noteId}")
    public ResponseEntity<Note> query(
            @ApiParam(value = "项目id", required = true)
            @RequestParam Integer projectId,
            @ApiParam(value = "gilab Issue ID", required = true)
            @RequestParam Integer issueIid,
            @ApiParam(value = "gitlab Note Id", required = true)
            @PathVariable int noteId) {
        return Optional.ofNullable(notesService.queryIssueNote(projectId, issueIid, noteId))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.note.query"));
    }

    /**
     * 创建Note
     *
     * @param projectId 项目id
     * @param issueIid  Issue Id
     * @param body      标记内容
     * @param createdAt 创建时间
     * @return Note
     */
    @ApiOperation(value = "创建Note")
    @PostMapping
    public ResponseEntity<Note> create(
            @ApiParam(value = "项目id", required = true)
            @RequestParam Integer projectId,
            @ApiParam(value = "Issue Id", required = true)
            @RequestParam Integer issueIid,
            @ApiParam(value = "Note Content", required = true)
            @RequestParam String body,
            @ApiParam(value = "创建时间", required = true)
            @RequestParam Date createdAt) {
        return Optional.ofNullable(notesService.createIssueNote(projectId, issueIid, body, createdAt))
                .map(result -> new ResponseEntity<>(result, HttpStatus.CREATED))
                .orElseThrow(() -> new CommonException("error.note.create"));
    }

    /**
     * 更新Note
     *
     * @param projectId 项目id
     * @param issueIid  Issue Id
     * @param noteId    标记 Id
     * @param body      Note Content
     * @return Note
     */
    @ApiOperation(value = "更新Note")
    @PutMapping
    public ResponseEntity<Note> update(
            @ApiParam(value = "gilab Project ID", required = true)
            @RequestParam Integer projectId,
            @ApiParam(value = "gilab Issue ID", required = true)
            @RequestParam Integer issueIid,
            @ApiParam(value = "gilab Note ID", required = true)
            @RequestParam Integer noteId,
            @ApiParam(value = "gitlab Note Content", required = true)
            @RequestParam String body) {
        return Optional.ofNullable(notesService.updateIssueNote(projectId, issueIid, noteId, body))
                .map(result -> new ResponseEntity<>(result, HttpStatus.CREATED))
                .orElseThrow(() -> new CommonException("error.note.update"));
    }

    /**
     * 删除Note
     *
     * @param projectId 项目id
     * @param issueIid  Issue Id
     * @param noteId    标记 Id
     */
    @ApiOperation(value = "删除Note")
    @DeleteMapping
    public ResponseEntity delete(
            @ApiParam(value = "gilab Project ID", required = true)
            @RequestParam Integer projectId,
            @ApiParam(value = "gilab Issue ID", required = true)
            @RequestParam Integer issueIid,
            @ApiParam(value = "gitlab Note ID", required = true)
            @RequestParam Integer noteId) {
        notesService.deleteIssueNote(projectId, issueIid, noteId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
