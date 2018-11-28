package io.choerodon.gitlab.app.service;

import java.util.List;

import org.gitlab4j.api.models.ImpersonationToken;
import org.gitlab4j.api.models.User;

public interface UserService {

    /**
     * 获得当前用户信息
     *
     * @return User
     */
    User queryCurrentUser();

    /**
     * 创建用户
     *
     * @param password      密码
     * @param projectsLimit 创建项目上限
     * @param user          User
     * @return User
     */
    User createUser(User user, String password, Integer projectsLimit);

    /**
     * 根据用户ID获得用户信息
     *
     * @param userId 用户id
     * @return User
     */
    User queryUserByUserId(Integer userId);

    /**
     * 根据用户名获得用户信息
     *
     * @param userName 用户名
     * @return User
     */
    User queryUserByUsername(String userName);

    /**
     * 查找所有用户
     *
     * @param perPage 每页大小
     * @param page    页数
     * @param active  是否有效
     * @return List
     */
    List<User> listUser(Integer perPage, Integer page, Boolean active);

    /**
     * 根据用户Id删除用户
     *
     * @param userId 用户Id
     */
    void deleteUserByUserId(Integer userId);

    /**
     * 根据用户名更新用户
     *
     * @param userId     用户Id
     * @param projectsLimit 创建项目上限
     * @param user          用户信息
     */
    User updateUserByUserId(Integer userId, User user, Integer projectsLimit);

    /**
     * 创建用户的Aceess_Token
     *
     * @param userId 用户Id
     * @return ImpersonationToken
     */
    ImpersonationToken createUserAccessToken(Integer userId);

    /**
     * 获取用户的Aceess_Token
     *
     * @param userId 用户Id
     * @return List
     */
    List<ImpersonationToken> listUserAccessToken(Integer userId);

    /**
     * 根据用户Id启用用户
     *
     * @param userId 用户Id
     */
    void enabledUserByUserId(Integer userId);

    /**
     * 根据用户Id禁用用户
     *
     * @param userId 用户Id
     */
    void disEnabledUserByUserId(Integer userId);
}
