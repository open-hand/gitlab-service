package io.choerodon.gitlab.app.service.impl;

import java.util.List;

import io.choerodon.core.exception.FeignException;
import io.choerodon.gitlab.app.service.UserService;
import io.choerodon.gitlab.infra.common.client.Gitlab4jClient;
import org.gitlab4j.api.Constants;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.UserApi;
import org.gitlab4j.api.models.Email;
import org.gitlab4j.api.models.ImpersonationToken;
import org.gitlab4j.api.models.User;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    private Gitlab4jClient gitlab4jclient;

    public UserServiceImpl(Gitlab4jClient gitlab4jclient) {
        this.gitlab4jclient = gitlab4jclient;
    }

    @Override
    public User createUser(User user, String password, Integer projectsLimit) {
        try {
            return gitlab4jclient.getGitLabApi()
                    .getUserApi()
                    .createUser(user, password, projectsLimit);
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public List<User> listUser(Integer perPage, Integer page, Boolean active) {
        try {
            UserApi userApi = gitlab4jclient.getGitLabApi().getUserApi();
            return active
                    ? userApi.getActiveUsers(page, perPage)
                    : userApi.getUsers(page, perPage);
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public User queryCurrentUser() {
        try {
            return gitlab4jclient.getGitLabApi().getUserApi().getCurrentUser();
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public User queryUserByUserId(Integer userId) {
        try {
            return gitlab4jclient.getGitLabApi()
                    .getUserApi().getUser(userId);
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public User queryUserByUsername(String userName) {
        try {
            return gitlab4jclient.getGitLabApi()
                    .getUserApi()
                    .getUser(userName);
        } catch (GitLabApiException e) {
            return null;
        }
    }

    @Override
    public void deleteUserByUserId(Integer userId) {
        try {
            UserApi userApi = gitlab4jclient.getGitLabApi().getUserApi();
            userApi.deleteUser(userId);
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public User updateUserByUserId(Integer userId, User user, Integer projectsLimit) {
        UserApi userApi = gitlab4jclient.getGitLabApi().getUserApi();
        try {
            user.setId(userId);
            user.setSkipReconfirmation(true);
            User oldUser = userApi.getUser(userId);
            user = userApi.modifyUser(user, null, projectsLimit);
            //删除用户的二级邮箱，否则更新前的邮箱无法在注册
            if (!oldUser.getEmail().equals(user.getEmail())) {
                List<Email> emails = userApi.findEmails(userId);
                for (Email email : emails) {
                    if (!email.getEmail().equals(user.getEmail())) {
                        userApi.deleteEmail(userId, email.getId());
                    }
                }
            }
            return user;
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public void enabledUserByUserId(Integer userId) {
        UserApi userApi = gitlab4jclient.getGitLabApi().getUserApi();
        try {
            userApi.unblockUser(userId);
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public void disEnabledUserByUserId(Integer userId) {
        UserApi userApi = gitlab4jclient.getGitLabApi().getUserApi();
        try {
            userApi.blockUser(userId);
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public ImpersonationToken createUserAccessToken(Integer userId) {
        UserApi userApi = gitlab4jclient.getGitLabApi().getUserApi();
        try {
            User user = userApi.getUser(userId);
            if (user == null) {
                throw new FeignException("error.users.get");
            }
            return userApi.createImpersonationToken(
                    user.getId(),
                    "myToken",
                    ImpersonationToken.Scope.values());
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public List<ImpersonationToken> listUserAccessToken(Integer userId) {
        UserApi userApi = gitlab4jclient.getGitLabApi().getUserApi();
        try {
            User user = userApi.getUser(userId);
            if (user == null) {
                throw new FeignException("error.users.get");
            }
            return userApi.getImpersonationTokens(user.getId(), Constants.ImpersonationState.ACTIVE);
        } catch (GitLabApiException e) {
            throw new FeignException(e.getMessage(), e);
        }
    }
}
