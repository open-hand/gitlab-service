package io.choerodon.gitlab.api.controller.v1;

import java.util.List;
import java.util.Optional;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.gitlab4j.api.models.ImpersonationToken;
import org.gitlab4j.api.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.choerodon.core.exception.FeignException;
import io.choerodon.gitlab.app.service.UserService;


@RestController
@RequestMapping(value = "/v1/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 获得当前用户信息
     *
     * @return User
     */
    @ApiOperation(value = "获得当前用户信息")
    @GetMapping(value = "/currentUser")
    public ResponseEntity<User> queryCurrentUser() {
        return Optional.ofNullable(userService.queryCurrentUser())
                .map(target -> new ResponseEntity<>(target, HttpStatus.OK))
                .orElseThrow(() -> new FeignException("error.user.get"));
    }

    /**
     * 创建用户
     *
     * @param password      密码
     * @param projectsLimit 创建项目上限
     * @param user          User
     * @return User
     */
    @ApiOperation(value = "创建用户")
    @PostMapping
    public ResponseEntity<User> create(
            @ApiParam(value = "密码", required = true)
            @RequestParam String password,
            @ApiParam(value = "创建项目上限")
            @RequestParam(required = false) Integer projectsLimit,
            @ApiParam(value = "用户信息")
            @RequestBody User user) {
        return Optional.ofNullable(userService.createUser(user, password, projectsLimit))
                .map(result -> new ResponseEntity<>(result, HttpStatus.CREATED))
                .orElseThrow(() -> new FeignException("error.users.create"));
    }

    /**
     * 查找所有用户
     *
     * @param perPage 每页大小
     * @param page    页数
     * @param active  是否有效
     * @return List
     */
    @ApiOperation(value = "查找所有用户")
    @GetMapping(value = "/list")
    public ResponseEntity<List<User>> list(
            @ApiParam(value = "每页大小", required = true)
            @RequestParam(defaultValue = "5") Integer perPage,
            @ApiParam(value = "页数", required = true)
            @RequestParam(defaultValue = "0") Integer page,
            @ApiParam(value = "是否有效", required = true)
            @RequestParam(defaultValue = "true") Boolean active) {
        return Optional.ofNullable(userService.listUser(perPage, page, active))
                .map(target -> new ResponseEntity<>(target, HttpStatus.OK))
                .orElseThrow(() -> new FeignException("error.users.queryAll"));
    }

    /**
     * 根据用户ID获得用户信息
     *
     * @param userId 用户id
     * @return User
     */
    @ApiOperation(value = "根据用户ID获得用户信息")
    @GetMapping(value = "/{userId}")
    public ResponseEntity<User> queryUserByUserId(
            @ApiParam(value = "用户ID", required = true)
            @PathVariable Integer userId) {
        return Optional.ofNullable(userService.queryUserByUserId(userId))
                .map(target -> new ResponseEntity<>(target, HttpStatus.OK))
                .orElseThrow(() -> new FeignException("error.users.get"));
    }

    /**
     * 根据用户Id获得用户信息
     *
     * @param username 用户Id
     * @return User
     */
    @ApiOperation(value = "根据用户Id获得用户信息")
    @GetMapping(value = "/{username}/details")
    public ResponseEntity<User> queryUserByUsername(
            @ApiParam(value = "用户Id", required = true)
            @PathVariable String username) {
        return Optional.ofNullable(userService.queryUserByUsername(username))
                .map(target -> new ResponseEntity<>(target, HttpStatus.OK))
                .orElse(new ResponseEntity<>(new User(), HttpStatus.NO_CONTENT));
    }

    /**
     * 根据用户Id删除用户
     *
     * @param userId 用户Id
     */
    @ApiOperation(value = "删除用户")
    @DeleteMapping(value = "{userId}")
    public ResponseEntity deleteUserByUserId(
            @ApiParam(value = "用户Id", required = true)
            @PathVariable Integer userId) {
        userService.deleteUserByUserId(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 根据用户Id更新用户
     *
     * @param userId        用户Id
     * @param projectsLimit 创建项目上限
     * @param user          用户信息
     */
    @ApiOperation(value = "根据用户Id更新用户")
    @PutMapping(value = "/{userId}")
    public ResponseEntity<User> updateUserByUserId(
            @ApiParam(value = "用户Id", required = true)
            @PathVariable Integer userId,
            @ApiParam(value = "创建项目上限")
            @RequestParam(required = false) Integer projectsLimit,
            @ApiParam(value = "用户信息")
            @RequestBody User user) {
        return Optional.ofNullable(userService.updateUserByUserId(userId, user, projectsLimit))
                .map(target -> new ResponseEntity<>(target, HttpStatus.CREATED))
                .orElseThrow(() -> new FeignException("error.users.username.update"));
    }

    /**
     * 根据用户Id禁用用户
     *
     * @param userId 用户Id
     */
    @ApiOperation(value = "根据用户Id禁用用户")
    @PutMapping(value = "/{userId}/dis_enabled")
    public ResponseEntity disEnabledUserByUserId(
            @ApiParam(value = "用户Id", required = true)
            @PathVariable Integer userId) {
        userService.disEnabledUserByUserId(userId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 根据用户Id启用用户
     *
     * @param userId 用户Id
     */
    @ApiOperation(value = "根据用户Id启用用户")
    @PutMapping(value = "/{userId}/is_enabled")
    public ResponseEntity enabledUserByUserId(
            @ApiParam(value = "用户Id", required = true)
            @PathVariable Integer userId) {
        userService.enabledUserByUserId(userId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 创建用户的Access_Token
     *
     * @param userId 用户Id
     * @return ImpersonationToken
     */
    @ApiOperation(value = "创建用户的Access_Token")
    @PostMapping(value = "/{userId}/impersonation_tokens")
    public ResponseEntity<ImpersonationToken> createUserAccessToken(@PathVariable Integer userId) {
        return Optional.ofNullable(userService.createUserAccessToken(userId))
                .map(target -> new ResponseEntity<>(target, HttpStatus.OK))
                .orElseThrow(() -> new FeignException("error.access_token.get"));
    }

    /**
     * 获取用户的Access_Token
     *
     * @param userId 用户Id
     * @return List
     */
    @ApiOperation(value = "获取用户的Access_Token")
    @GetMapping(value = "/{userId}/impersonation_tokens")
    public ResponseEntity<List<ImpersonationToken>> listUserAccessToken(@PathVariable Integer userId) {
        return Optional.ofNullable(userService.listUserAccessToken(userId))
                .map(target -> new ResponseEntity<>(target, HttpStatus.OK))
                .orElseThrow(() -> new FeignException("error.access_token.get"));
    }

    /**
     * 校验用户邮箱是否在gitlab已存在
     *
     * @param email 用户邮箱
     * @return Boolean
     */
    @ApiOperation(value = "校验用户邮箱是否在gitlab已存在")
    @GetMapping(value = "/email/check")
    public ResponseEntity<Boolean> checkEmailIsExist(
            @ApiParam(value = "用户邮箱", required = true)
            @RequestParam(value = "email") String email) {
        return Optional.ofNullable(userService.checkEmailIsExist(email))
                .map(target -> new ResponseEntity<>(target, HttpStatus.OK))
                .orElseThrow(() -> new FeignException("error.user.email.check"));
    }

    /**
     * 判断用户是否是admin
     *
     * @param userId gitlab用户id
     * @return true表示是
     */
    @GetMapping("/{userId}/admin")
    public ResponseEntity<Boolean> checkIsAdmin(@PathVariable("userId") Integer userId) {
        return new ResponseEntity<>(userService.checkIsAdmin(userId), HttpStatus.OK);
    }

    /**
     * 为用户添加admin权限
     *
     * @param userId gitlab用户id
     * @return true表示加上了
     */
    @PutMapping("/{userId}/admin")
    public ResponseEntity<Boolean> setAdmin(@PathVariable("userId") Integer userId) {
        return new ResponseEntity<>(userService.setAdmin(userId), HttpStatus.OK);
    }

    /**
     * 删除用户admin权限
     *
     * @param userId gitlab用户id
     * @return true表示删除了
     */
    @DeleteMapping("/{userId}/admin")
    public ResponseEntity<Boolean> deleteAdmin(@PathVariable("userId") Integer userId) {
        return new ResponseEntity<>(userService.deleteAdmin(userId), HttpStatus.OK);
    }
}
