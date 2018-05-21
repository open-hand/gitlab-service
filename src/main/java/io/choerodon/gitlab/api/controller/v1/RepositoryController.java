package io.choerodon.gitlab.api.controller.v1;

import java.util.List;
import java.util.Optional;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.gitlab4j.api.models.Branch;
import org.gitlab4j.api.models.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.choerodon.core.exception.CommonException;
import io.choerodon.gitlab.app.service.RepositoryService;

@RestController
@RequestMapping("/v1/projects/{projectId}/repository")
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
     * @return Branch
     */
    @ApiOperation(value = "创建新分支")
    @PostMapping("/branches")
    public ResponseEntity<Branch> createBranch(
            @ApiParam(value = "项目id", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "分支名", required = true)
            @RequestParam("name") String name,
            @ApiParam(value = "源分支名", required = true)
            @RequestParam("source") String source) {
        return Optional.ofNullable(repositoryService.createBranch(projectId, name, source))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.branch.create"));
    }

    /**
     * 获取tag列表
     *
     * @param projectId 项目id
     * @param username  用户名
     * @return List
     */
    @ApiOperation(value = "获取tag列表")
    @GetMapping("/tags")
    public ResponseEntity<List<Tag>> listTags(
            @ApiParam(value = "项目id", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "用户名")
            @RequestParam(value = "username", required = false) String username) {
        return Optional.ofNullable(repositoryService.listTags(projectId, username))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.tag.get"));
    }

    /**
     * 分页获取tag列表
     *
     * @param projectId 项目id
     * @param page      页码
     * @param perPage   每页数量
     * @param username  用户名
     * @return List
     */
    @ApiOperation(value = "分页获取tag列表")
    @GetMapping("/tags/page")
    public ResponseEntity<List<Tag>> listTagsByPage(
            @ApiParam(value = "项目id", required = true)
            @PathVariable Integer projectId,
            @RequestParam("page") int page,
            @RequestParam("perPage") int perPage,
            @ApiParam(value = "用户名")
            @RequestParam(value = "username", required = false) String username) {
        return Optional.ofNullable(repositoryService.listTagsByPage(projectId, page, perPage, username))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.tag.getPage"));
    }


    /**
     * 创建tag
     *
     * @param projectId 项目id
     * @param name      标签名
     * @param ref       标签源
     * @param username  用户名
     * @return Tag
     */
    @ApiOperation(value = "创建tag")
    @PostMapping("/tags")
    public ResponseEntity<Tag> createTag(
            @ApiParam(value = "项目id", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "标签名", required = true)
            @RequestParam("name") String name,
            @ApiParam(value = "标签源", required = true)
            @RequestParam("ref") String ref,
            @ApiParam(value = "用户名")
            @RequestParam(value = "username", required = false)
                    String username) {
        return Optional.ofNullable(repositoryService.createTag(projectId, name, ref, username))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.tag.create"));
    }

    /**
     * 根据分支名删除分支
     *
     * @param projectId  项目id
     * @param branchName 分支名
     * @param username   用户名
     */
    @ApiOperation(value = "根据分支名删除分支")
    @DeleteMapping("/branches")
    public ResponseEntity deleteBranch(
            @ApiParam(value = "项目id", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "要删除的分支名", required = true)
            @RequestParam("branchName") String branchName,
            @ApiParam(value = "用户名")
            @RequestParam(value = "username", required = false) String username) {
        repositoryService.deleteBranch(projectId, branchName, username);
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
    @GetMapping("/branches/{branchName}")
    public ResponseEntity<Branch> queryBranchByName(
            @ApiParam(value = "工程id", required = true)
            @PathVariable Integer projectId,
            @ApiParam(value = "要查询的分支名", required = true)
            @PathVariable("branchName") String branchName) {
        return Optional.ofNullable(repositoryService.queryBranchByName(projectId, branchName))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.branch.query"));
    }

    /**
     * 获取项目下所有分支
     *
     * @param projectId 项目id
     * @return List
     */
    @ApiOperation(value = "获取工程下所有分支")
    @GetMapping("/branches")
    public ResponseEntity<List<Branch>> listBranches(
            @ApiParam(value = "项目id", required = true) @PathVariable Integer projectId) {
        return Optional.ofNullable(repositoryService.listBranches(projectId))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.branch.list"));
    }

    /**
     * 项目下创建readme
     *
     * @param projectId 项目id
     * @return boolean
     */
    @ApiOperation(value = "项目下创建readme")
    @PostMapping("/file")
    public ResponseEntity<Boolean> createFile(
            @ApiParam(value = "项目id", required = true) @PathVariable Integer projectId,
            @RequestParam("userName") String userName) {
        return Optional.ofNullable(repositoryService.createFile(projectId, userName))
                .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.readme.create"));
    }

}
