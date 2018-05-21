package io.choerodon.gitlab.app.service.impl;

import java.util.List;

import org.gitlab4j.api.Constants;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.UserApi;
import org.gitlab4j.api.models.ImpersonationToken;
import org.gitlab4j.api.models.User;
import org.springframework.stereotype.Service;

import io.choerodon.core.exception.CommonException;
import io.choerodon.gitlab.app.service.UserService;
import io.choerodon.gitlab.infra.common.client.Gitlab4jClient;


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
            throw new CommonException(e.getMessage());
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
            throw new CommonException(e.getMessage());
        }
    }

    @Override
    public User queryCurrentUser() {
        try {
            return gitlab4jclient.getGitLabApi().getUserApi().getCurrentUser();
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage());
        }
    }

    @Override
    public User queryUserByUserId(Integer userId) {
        try {
            return gitlab4jclient.getGitLabApi()
                    .getUserApi().getUser(userId);
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage());
        }
    }

    @Override
    public User queryUserByUsername(String userName) {
        try {
            return gitlab4jclient.getGitLabApi()
                    .getUserApi()
                    .getUser(gitlab4jclient.getRealUsername(userName));
        } catch (GitLabApiException e) {
            return null;
        }
    }

    @Override
    public void deleteUserByUsername(String username) {
        try {
            UserApi userApi = gitlab4jclient.getGitLabApi().getUserApi();
            User deleteUser = userApi.getUser(username);
            userApi.deleteUser(deleteUser.getId());
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage());
        }
    }

    @Override
    public User updateUserByUsername(String username, User user, Integer projectsLimit) {
        UserApi userApi = gitlab4jclient.getGitLabApi().getUserApi();
        username = gitlab4jclient.getRealUsername(username);
        user.setUsername(username);
        try {
            user.setId(userApi.getUser(username).getId());
            return userApi.modifyUser(user, null, projectsLimit);
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage());
        }
    }

    @Override
    public void enabledUserByUsername(String username) {
        UserApi userApi = gitlab4jclient.getGitLabApi().getUserApi();
        username = gitlab4jclient.getRealUsername(username);
        try {
            userApi.unblockUser(userApi.getUser(username).getId());
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage());
        }
    }

    @Override
    public void disEnabledUserByUsername(String username) {
        UserApi userApi = gitlab4jclient.getGitLabApi().getUserApi();
        username = gitlab4jclient.getRealUsername(username);
        try {
            userApi.blockUser(userApi.getUser(username).getId());
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage());
        }
    }

    @Override
    public ImpersonationToken createUserAccessToken(String username) {
        UserApi userApi = gitlab4jclient.getGitLabApi().getUserApi();
        try {
            User user = userApi.getUser(gitlab4jclient.getRealUsername(username));
            if (user == null) {
                throw new CommonException("error.users.get");
            }
            return userApi.createImpersonationToken(
                    user.getId(),
                    "myToken",
                    ImpersonationToken.Scope.values());
        } catch (GitLabApiException g) {
            throw new CommonException(g.getMessage());
        }
    }

    @Override
    public List<ImpersonationToken> listUserAccessToken(String username) {
        UserApi userApi = gitlab4jclient.getGitLabApi().getUserApi();
        try {
            User user = userApi.getUser(gitlab4jclient.getRealUsername(username));
            if (user == null) {
                throw new CommonException("error.users.get");
            }
            return userApi.getImpersonationTokens(user.getId(), Constants.ImpersonationState.ACTIVE);
        } catch (GitLabApiException g) {
            throw new CommonException(g.getMessage());
        }
    }
}
