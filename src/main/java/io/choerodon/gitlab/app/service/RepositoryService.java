package io.choerodon.gitlab.app.service;

import java.util.List;

import org.gitlab4j.api.models.Branch;
import org.gitlab4j.api.models.Tag;


public interface RepositoryService {

    /**
     * 创建新分支
     *
     * @param projectId  项目id
     * @param branchName 分支名
     * @param source     源分支名
     * @return Branch
     */
    Branch createBranch(Integer projectId, String branchName, String source);

    /**
     * 获取tag列表
     *
     * @param projectId 项目id
     * @param username  用户名
     * @return List
     */
    List<Tag> listTags(Integer projectId, String username);

    /**
     * 分页获取tag列表
     *
     * @param projectId 项目id
     * @param page      页码
     * @param perPage   每页数量
     * @param username  用户名
     * @return List
     */
    List<Tag> listTagsByPage(Integer projectId, int page, int perPage, String username);

    /**
     * 创建tag
     *
     * @param projectId 项目id
     * @param tagName   标签名
     * @param ref       标签源
     * @param username  用户名
     * @return Tag
     */
    Tag createTag(Integer projectId, String tagName, String ref, String username);

    /**
     * 根据分支名删除分支
     *
     * @param projectId  项目id
     * @param branchName 分支名
     * @param username   用户名
     */
    void deleteBranch(Integer projectId, String branchName, String username);

    /**
     * 根据分支名查询分支
     *
     * @param projectId  项目id
     * @param branchName 分支名
     * @return Branch
     */
    Branch queryBranchByName(Integer projectId, String branchName);


    /**
     * 获取项目下所有分支
     *
     * @param projectId 项目id
     * @return List
     */
    List<Branch> listBranches(Integer projectId);

    boolean createFile(Integer projectId, String userName);
}
