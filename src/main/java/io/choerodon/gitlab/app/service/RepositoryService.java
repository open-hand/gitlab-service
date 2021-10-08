package io.choerodon.gitlab.app.service;

import java.io.InputStream;
import java.util.List;

import org.gitlab4j.api.models.Branch;
import org.gitlab4j.api.models.CompareResults;
import org.gitlab4j.api.models.RepositoryFile;
import org.gitlab4j.api.models.Tag;

import io.choerodon.gitlab.infra.dto.AppExternalConfigDTO;


public interface RepositoryService {

    /**
     * 创建新分支
     *
     * @param projectId  项目id
     * @param branchName 分支名
     * @param source     源分支名
     * @param userId     用户Id
     * @return Branch
     */
    Branch createBranch(Integer projectId, String branchName, String source, Integer userId);

    /**
     * 获取tag列表
     *
     * @param projectId 项目id
     * @param userId    用户Id
     * @return List
     */
    List<Tag> listTags(Integer projectId, Integer userId);

    /**
     * 分页获取tag列表
     *
     * @param projectId 项目id
     * @param page      页码
     * @param perPage   每页数量
     * @param userId    用户Id
     * @return List
     */
    List<Tag> listTagsByPage(Integer projectId, int page, int perPage, Integer userId);

    /**
     * 创建tag
     *
     * @param projectId    项目id
     * @param tagName      标签名
     * @param ref          标签源
     * @param userId       用户Id
     * @param msg          描述
     * @param releaseNotes 发布日志
     * @return Tag
     */
    Tag createTag(Integer projectId, String tagName, String ref, String msg, String releaseNotes, Integer userId);

    /**
     * 根据标签名删除tag
     *
     * @param projectId 项目id
     * @param tagName   标签名
     * @param userId    用户Id
     */
    void deleteTag(Integer projectId, String tagName, Integer userId);

    /**
     * 更新 tag
     *
     * @param projectId    项目id
     * @param name         标签名
     * @param releaseNotes 发布日志
     * @return Tag
     */
    Tag updateTagRelease(Integer projectId, String name, String releaseNotes, Integer userId);

    /**
     * 根据分支名删除分支
     *
     * @param projectId  项目id
     * @param branchName 分支名
     * @param userId     用户Id
     */
    void deleteBranch(Integer projectId, String branchName, Integer userId);

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
     * @param userId    用户Id
     * @return List
     */
    List<Branch> listBranches(Integer projectId, Integer userId);


//    /**
//     * 项目下创建readme
//     *
//     * @param projectId 项目id
//     * @param userId    用户Id
//     */
//    boolean createFile(Integer projectId, Integer userId);


    /**
     * 项目下获取file
     *
     * @param projectId 项目id
     * @param commit    the commit SHA or branch name
     * @param filePath  file path
     * @return file
     */
    RepositoryFile getFile(Integer projectId, String commit, String filePath, AppExternalConfigDTO appExternalConfigDTO);


    /**
     * 项目下获取diffs
     *
     * @param projectId 项目id
     * @param from      the commit SHA or branch name
     * @param to        the commit SHA or branch name
     * @return CompareResults
     */
    CompareResults getDiffs(Integer projectId, String from, String to);

    RepositoryFile createFile(Integer projectId, String path, String content, String commitMessage, Integer userId, String branchName, AppExternalConfigDTO appExternalConfigDTO);

    RepositoryFile updateFile(Integer projectId, String path, String content, String commitMessage, Integer userId, AppExternalConfigDTO appExternalConfigDTO);

    void deleteFile(Integer projectId, String path, String commitMessage, Integer userId, AppExternalConfigDTO appExternalConfigDTO);

    /**
     * 下载压缩包
     *
     * @param projectId gitlab项目id
     * @param userId    gitlab用户id
     * @param commitSha 要下载的commit
     * @return tgz压缩包的字节数组
     */
    byte[] downloadArchive(Integer projectId, Integer userId, String commitSha);


    /**
     * 下载压缩包
     *
     * @param projectId gitlab项目id
     * @param userId    gitlab用户id
     * @param commitSha 要下载的commit或者分支
     * @param format    文件类型
     * @return
     */
    InputStream downloadArchiveByFormat(Integer projectId, Integer userId, String commitSha, String format);
}
