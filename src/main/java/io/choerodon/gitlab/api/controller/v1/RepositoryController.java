package io.choerodon.gitlab.api.controller.v1;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.gitlab4j.api.models.Branch;
import org.gitlab4j.api.models.CompareResults;
import org.gitlab4j.api.models.RepositoryFile;
import org.gitlab4j.api.models.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import io.choerodon.core.exception.FeignException;
import io.choerodon.gitlab.api.vo.FileCreationVO;
import io.choerodon.gitlab.api.vo.FileDeleteVO;
import io.choerodon.gitlab.api.vo.GitlabTransferVO;
import io.choerodon.gitlab.app.service.RepositoryService;
import io.choerodon.gitlab.infra.dto.AppExternalConfigDTO;

@RestController
@RequestMapping(value = "/v1/projects/{projectId}/repository")
public class RepositoryController {

    private RepositoryService repositoryService;

    public RepositoryController(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    /**
     * 创建新分支
     *
     * @param projectId 项目id
     * @param name      分支名
     * @param source    源分支名
     * @param userId    用户Id
     * @return Branch
     */
    @ApiOperation(value = "创建新分支")
    @PostMapping(value = "/branches")
    public ResponseEntity<Branch> createBranch(
            @ApiParam(value = "项目id", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "分支名&源分支名", required = true)
            @RequestBody @Validated({GitlabTransferVO.CreateBranch.class}) GitlabTransferVO gitlabTransferVO,
            @ApiParam(value = "用户Id")
            @RequestParam(value = "userId") Integer userId
    ) {
        return Optional.ofNullable(repositoryService.createBranch(projectId, gitlabTransferVO.getBranchName(), gitlabTransferVO.getSourceBranch(), userId))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new FeignException("error.branch.create"));
    }

    /**
     * 获取tag列表
     *
     * @param projectId 项目id
     * @param userId    用户Id
     * @return List
     */
    @ApiOperation(value = "获取tag列表")
    @GetMapping(value = "/tags")
    public ResponseEntity<List<Tag>> listTags(
            @ApiParam(value = "项目id", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "用户Id")
            @RequestParam(value = "userId", required = false) Integer userId) {
        return Optional.ofNullable(repositoryService.listTags(projectId, userId))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new FeignException("error.tag.get"));
    }

    /**
     * 分页获取tag列表
     *
     * @param projectId 项目id
     * @param page      页码
     * @param perPage   每页数量
     * @param userId    用户Id
     * @return List
     */
    @ApiOperation(value = "分页获取tag列表")
    @GetMapping(value = "/tags/page")
    public ResponseEntity<List<Tag>> listTagsByPage(
            @ApiParam(value = "项目id", required = true)
            @PathVariable Integer projectId,
            @RequestParam("page") int page,
            @RequestParam("perPage") int perPage,
            @ApiParam(value = "用户Id")
            @RequestParam(value = "userId", required = false) Integer userId) {
        return Optional.ofNullable(repositoryService.listTagsByPage(projectId, page, perPage, userId))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new FeignException("error.tag.getPage"));
    }


    /**
     * 创建tag
     *
     * @param projectId 项目id
     * @param name      标签名
     * @param ref       标签源
     * @param userId    用户Id
     * @return Tag
     */
    @ApiOperation(value = "创建tag")
    @PostMapping(value = "/tags")
    public ResponseEntity<Tag> createTag(
            @ApiParam(value = "项目id", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "标签名&标签源&标签描述&发布日志", required = true)
            @RequestBody @Validated({GitlabTransferVO.CreateTag.class}) GitlabTransferVO gitlabTransferVO,
            @ApiParam(value = "用户Id")
            @RequestParam(value = "userId", required = false) Integer userId) {
        return Optional.ofNullable(repositoryService.createTag(projectId, gitlabTransferVO.getTagName(), gitlabTransferVO.getRef(), gitlabTransferVO.getMsg(), gitlabTransferVO.getReleaseNotes(), userId))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new FeignException("error.tag.create"));
    }

    /**
     * 创建新分支
     * 更新 tag
     *
     * @param projectId    项目id
     * @param name         标签名
     * @param releaseNotes 发布日志
     * @return Tag
     */
    @ApiOperation(value = "更新tag")
    @PutMapping(value = "/tags")
    public ResponseEntity<Tag> updateTagRelease(
            @ApiParam(value = "项目id", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "标签名", required = true)
            @RequestBody @Validated({GitlabTransferVO.UpdateTag.class}) GitlabTransferVO gitlabTransferVO,
            @ApiParam(value = "用户Id")
            @RequestParam(value = "userId", required = false) Integer userId) {
        return Optional.ofNullable(repositoryService.updateTagRelease(projectId, gitlabTransferVO.getTagName(), gitlabTransferVO.getReleaseNotes(), userId))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new FeignException("error.tag.create"));
    }

    /**
     * 根据标签名删除标签
     *
     * @param projectId 项目id
     * @param name      标签名
     * @param userId    用户Id
     */
    @ApiOperation(value = "删除tag")
    @DeleteMapping(value = "/tags")
    public ResponseEntity deleteTag(
            @ApiParam(value = "项目id", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "标签名", required = true)
            @RequestBody String name,
            @ApiParam(value = "用户Id")
            @RequestParam(value = "userId", required = false)
                    Integer userId) {
        repositoryService.deleteTag(projectId, name, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 根据分支名删除分支
     *
     * @param projectId  项目id
     * @param branchName 分支名
     * @param userId     用户Id
     */
    @ApiOperation(value = "根据分支名删除分支")
    @DeleteMapping(value = "/branches")
    public ResponseEntity deleteBranch(
            @ApiParam(value = "项目id", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "要删除的分支名", required = true)
            @RequestBody String branchName,
            @ApiParam(value = "用户Id")
            @RequestParam(value = "userId", required = false) Integer userId) {
        repositoryService.deleteBranch(projectId, branchName, userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 根据分支名查询分支
     *
     * @param projectId  项目id
     * @param branchName 分支名
     * @return Branch
     */
    @ApiOperation(value = "根据分支名查询分支")
    @GetMapping(value = "/branches/{branchName}")
    public ResponseEntity<Branch> queryBranchByName(
            @ApiParam(value = "工程id", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "要查询的分支名", required = true)
            @PathVariable("branchName") String branchName) {
        return Optional.ofNullable(repositoryService.queryBranchByName(projectId, branchName))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new FeignException("error.branch.query"));
    }

    /**
     * 获取项目下所有分支
     *
     * @param projectId 项目id
     * @param userId    用户Id
     * @return List
     */
    @ApiOperation(value = "获取工程下所有分支")
    @GetMapping(value = "/branches")
    public ResponseEntity<List<Branch>> listBranches(
            @ApiParam(value = "项目id", required = true) @PathVariable Integer projectId,
            @ApiParam(value = "用户Id")
            @RequestParam(value = "userId", required = false) Integer userId) {
        return Optional.ofNullable(repositoryService.listBranches(projectId, userId))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new FeignException("error.branch.list"));
    }

    /**
     * 项目下获取file
     *
     * @param projectId 项目id
     * @param commit    the commit SHA or branch name
     * @param filePath  file path
     * @return file
     */
    @ApiOperation(value = "项目下获取file")
    @GetMapping(value = "/{commit}/file")
    public ResponseEntity<RepositoryFile> getFile(
            @ApiParam(value = "项目id", required = true) @PathVariable Integer projectId,
            @ApiParam(value = "commit", required = true) @PathVariable String commit,
            @ApiParam(value = "file path", required = true) @RequestParam(value = "file_path") String filePath) {
        return Optional.ofNullable(repositoryService.getFile(projectId, commit, filePath, null))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new FeignException("error.file.get"));
    }


    /**
     * 项目下获取diffs
     *
     * @param projectId 项目id
     * @param from      the commit SHA or branch name
     * @param to        the commit SHA or branch name
     * @return CompareResults
     */
    @ApiOperation(value = "项目下获取diffs")
    @PostMapping(value = "/file/diffs")
    public ResponseEntity<CompareResults> getDiffs(
            @ApiParam(value = "项目id", required = true) @PathVariable Integer projectId,
            @ApiParam(value = "from", required = true)
            @RequestBody @Validated({GitlabTransferVO.GetDiffs.class}) GitlabTransferVO gitlabTransferVO) {
        return Optional.ofNullable(repositoryService.getDiffs(projectId, gitlabTransferVO.getFrom(), gitlabTransferVO.getTo()))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new FeignException("error.diffs.get"));
    }


    /**
     * 项目下创建File
     *
     * @param projectId      项目id
     * @param fileCreationVO 文件相关信息
     */
    @ApiOperation(value = "项目下创建File")
    @PostMapping(value = "/file")
    public ResponseEntity<RepositoryFile> createFile(
            @ApiParam(value = "项目id", required = true)
            @PathVariable Integer projectId,
            // TODO @Valid
            @RequestBody FileCreationVO fileCreationVO,
            AppExternalConfigDTO appExternalConfigDTO) {
        return Optional.ofNullable(repositoryService.createFile(projectId,
                fileCreationVO.getPath(),
                fileCreationVO.getContent(),
                fileCreationVO.getCommitMessage(),
                fileCreationVO.getUserId(),
                fileCreationVO.getBranchName(),
                appExternalConfigDTO))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new FeignException("error.file.create"));
    }


    /**
     * 项目下更新File
     *
     * @param projectId      项目id
     * @param fileCreationVO 文件信息
     */
    @ApiOperation(value = "项目下更新File")
    @PutMapping(value = "/file")
    public ResponseEntity<RepositoryFile> updateFile(
            @ApiParam(value = "项目id", required = true)
            @PathVariable Integer projectId,
            // TODO @Valid
            @RequestBody FileCreationVO fileCreationVO,
            AppExternalConfigDTO appExternalConfigDTO) {
        return Optional.ofNullable(repositoryService.updateFile(projectId,
                fileCreationVO.getPath(),
                fileCreationVO.getContent(),
                fileCreationVO.getCommitMessage(),
                fileCreationVO.getUserId(),
                appExternalConfigDTO))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new FeignException("error.file.update"));
    }


    /**
     * 项目下删除File
     *
     * @param projectId    项目id
     * @param fileDeleteVO 文件删除相关信息
     */
    @ApiOperation(value = "项目下删除File")
    @DeleteMapping(value = "/file")
    public ResponseEntity deleteFile(
            @ApiParam(value = "项目id", required = true)
            @PathVariable Integer projectId,
            // TODO @Valid
            @RequestBody FileDeleteVO fileDeleteVO) {
        repositoryService.deleteFile(projectId, fileDeleteVO.getPath(), fileDeleteVO.getCommitMessage(), fileDeleteVO.getUserId());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation(value = "项目下下载特定commit的压缩包/tgz压缩格式")
    @GetMapping("/archive")
    public ResponseEntity<byte[]> downloadArchive(
            @ApiParam(value = "项目id", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "用户Id")
            @RequestParam(value = "user_id") Integer userId,
            @RequestParam(value = "commit_sha") String commitSha) {
        MultiValueMap<String, String> httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.TRANSFER_ENCODING, "chunked");
        // 因为本身就是gzip压缩格式内容，这个头可以让浏览器将这个接口的内容自动解压，解压之后就是json格式了
        httpHeaders.add(HttpHeaders.CONTENT_ENCODING, "gzip");
        return new ResponseEntity<>(repositoryService.downloadArchive(projectId, userId, commitSha), httpHeaders, HttpStatus.OK);
    }

    @ApiOperation(value = "项目下下载特定commit的压缩包")
    @GetMapping("/archive_format")
    public ResponseEntity<InputStream> downloadArchiveByFormat(
            @ApiParam(value = "项目id", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "用户Id")
            @RequestParam(value = "user_id") Integer userId,
            @RequestParam(value = "commit_sha") String commitSha,
            @RequestParam(value = "format", required = false) String format) {
        return new ResponseEntity<>(repositoryService.downloadArchiveByFormat(projectId, userId, commitSha, format), HttpStatus.OK);
    }
}
