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

import io.choerodon.core.exception.CommonException;
import io.choerodon.gitlab.app.service.UserService;


@RestController
@RequestMapping("/v1/users")
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
    @GetMapping("/currentUser")
    public ResponseEntity<User> queryCurrentUser() {
        return Optional.ofNullable(userService.queryCurrentUser())
                .map(target -> new ResponseEntity<>(target, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.user.get"));
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
                .orElseThrow(() -> new CommonException("error.users.create"));
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
    @GetMapping("/list")
    public ResponseEntity<List<User>> list(
            @ApiParam(value = "每页大小", required = true)
            @RequestParam(defaultValue = "5") Integer perPage,
            @ApiParam(value = "页数", required = true)
            @RequestParam(defaultValue = "0") Integer page,
            @ApiParam(value = "是否有效", required = true)
            @RequestParam(defaultValue = "true") Boolean active) {
        return Optional.ofNullable(userService.listUser(perPage, page, active))
                .map(target -> new ResponseEntity<>(target, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.users.queryAll"));
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
                .orElseThrow(() -> new CommonException("error.users.get"));
    }

    /**
     * 根据用户名获得用户信息
     *
     * @param username 用户名
     * @return User
     */
    @ApiOperation(value = "根据用户名获得用户信息")
    @GetMapping(value = "/{username}/details")
    public ResponseEntity<User> queryUserByUsername(
            @ApiParam(value = "用户名", required = true)
            @PathVariable String username) {
        return Optional.ofNullable(userService.queryUserByUsername(username))
                .map(target -> new ResponseEntity<>(target, HttpStatus.OK))
                .orElse(new ResponseEntity<>(new User(), HttpStatus.NO_CONTENT));
    }

    /**
     * 根据用户名删除用户
     *
     * @param username 用户名
     */
    @ApiOperation(value = "删除用户")
    @DeleteMapping(value = "{username}")
    public ResponseEntity deleteUserByUsername(
            @ApiParam(value = "用户名", required = true)
            @PathVariable String username) {
        userService.deleteUserByUsername(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * 根据用户名更新用户
     *
     * @param username      用户名
     * @param projectsLimit 创建项目上限
     * @param user          用户信息
     */
    @ApiOperation(value = "根据用户名更新用户")
    @PutMapping(value = "/{username}")
    public ResponseEntity<User> updateUserByUsername(
            @ApiParam(value = "用户名", required = true)
            @PathVariable String username,
            @ApiParam(value = "创建项目上限")
            @RequestParam(required = false) Integer projectsLimit,
            @ApiParam(value = "用户信息")
            @RequestBody User user) {
        return Optional.ofNullable(userService.updateUserByUsername(username, user, projectsLimit))
                .map(target -> new ResponseEntity<>(target, HttpStatus.CREATED))
                .orElseThrow(() -> new CommonException("error.users.username.update"));
    }

    /**
     * 根据用户名禁用用户
     *
     * @param username 用户名
     */
    @ApiOperation(value = "根据用户名禁用用户")
    @PutMapping(value = "/{username}/dis_enabled")
    public ResponseEntity disEnabledUserByUsername(
            @ApiParam(value = "用户名", required = true)
            @PathVariable String username) {
        userService.disEnabledUserByUsername(username);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 根据用户名启用用户
     *
     * @param username 用户名
     */
    @ApiOperation(value = "根据用户名启用用户")
    @PutMapping(value = "/{username}/is_enabled")
    public ResponseEntity enabledUserByUsername(
            @ApiParam(value = "用户名", required = true)
            @PathVariable String username) {
        userService.enabledUserByUsername(username);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 创建用户的Access_Token
     *
     * @param username 用户名
     * @return ImpersonationToken
     */
    @ApiOperation(value = "创建用户的Access_Token")
    @PostMapping(value = "/{username}/impersonation_tokens")
    public ResponseEntity<ImpersonationToken> createUserAccessToken(@PathVariable String username) {
        return Optional.ofNullable(userService.createUserAccessToken(username))
                .map(target -> new ResponseEntity<>(target, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.access_token.get"));
    }

    /**
     * 获取用户的Access_Token
     *
     * @param username 用户名
     * @return List
     */
    @ApiOperation(value = "获取用户的Access_Token")
    @GetMapping(value = "/{username}/impersonation_tokens")
    public ResponseEntity<List<ImpersonationToken>> listUserAccessToken(@PathVariable String username) {
        return Optional.ofNullable(userService.listUserAccessToken(username))
                .map(target -> new ResponseEntity<>(target, HttpStatus.OK))
                .orElseThrow(() -> new CommonException("error.access_token.get"));
    }
}
