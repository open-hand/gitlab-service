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
     * 根据用户名删除用户
     *
     * @param username 用户名
     */
    void deleteUserByUsername(String username);

    /**
     * 根据用户名更新用户
     *
     * @param username      用户名
     * @param projectsLimit 创建项目上限
     * @param user          用户信息
     */
    User updateUserByUsername(String username, User user, Integer projectsLimit);

    /**
     * 创建用户的Aceess_Token
     *
     * @param username 用户名
     * @return ImpersonationToken
     */
    ImpersonationToken createUserAccessToken(String username);

    /**
     * 获取用户的Aceess_Token
     *
     * @param username 用户名
     * @return List
     */
    List<ImpersonationToken> listUserAccessToken(String username);

    /**
     * 根据用户名启用用户
     *
     * @param username 用户名
     */
    void enabledUserByUsername(String username);

    /**
     * 根据用户名禁用用户
     *
     * @param username 用户名
     */
    void disEnabledUserByUsername(String username);
}
