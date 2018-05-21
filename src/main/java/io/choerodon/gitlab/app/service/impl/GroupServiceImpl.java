package io.choerodon.gitlab.app.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.GroupApi;
import org.gitlab4j.api.models.AccessLevel;
import org.gitlab4j.api.models.Group;
import org.gitlab4j.api.models.Member;
import org.gitlab4j.api.models.Project;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import io.choerodon.core.exception.CommonException;
import io.choerodon.gitlab.api.dto.GroupDTO;
import io.choerodon.gitlab.api.dto.MemberDto;
import io.choerodon.gitlab.app.service.GroupService;
import io.choerodon.gitlab.infra.common.client.Gitlab4jClient;


@Service
public class GroupServiceImpl implements GroupService {

    private Gitlab4jClient gitlab4jclient;

    public GroupServiceImpl(Gitlab4jClient gitlab4jclient) {
        this.gitlab4jclient = gitlab4jclient;
    }

    @Override
    public GroupDTO createGroup(GroupDTO group, String userName) {
        GitLabApi gitLabApi = gitlab4jclient.getGitLabApi(userName);
        try {
            GroupApi groupApi = gitLabApi.getGroupApi();
            groupApi.addGroup(group.getName(), group.getPath(), group.getDescription(),
                    null, null, group.getVisibility(), null,
                    group.getRequestAccessEnabled(), group.getParentId(), group.getSharedRunnersMinutesLimit());
            Group group1 = gitLabApi.getGroupApi().getGroup(group.getPath());
            if (group1 == null) {
                return null;
            } else {
                GroupDTO groupDTO = new GroupDTO();
                BeanUtils.copyProperties(group1, groupDTO);
                gitLabApi.getProjectApi().createProject(group1.getId(), "issue");
                return groupDTO;
            }
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage());
        }
    }

    @Override
    public Group updateGroup(Integer groupId, String userName, Group group) {
        GitLabApi gitLabApi = gitlab4jclient.getGitLabApi(userName);
        try {
            return gitLabApi.getGroupApi().updateGroup(groupId, group.getName(), group.getPath(),
                    group.getDescription(), null, null, group.getVisibility(), null,
                    group.getRequestAccessEnabled(), group.getParentId(), group.getSharedRunnersMinutesLimit());
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage());
        }
    }

    @Override
    public void deleteGroup(Integer groupId) {
        String username = gitlab4jclient.getRealUsername(null);
        GroupApi groupApi = gitlab4jclient.getGitLabApiUser(username).getGroupApi();
        try {
            List<Member> members = groupApi.getMembers(groupId)
                    .stream().filter(t -> username.equals(t.getUsername())).collect(Collectors.toList());
            if (members != null && AccessLevel.OWNER.value.equals(members.get(0).getAccessLevel().value)) {
                groupApi.deleteGroup(groupId);
            } else {
                throw new CommonException("error.groups.deleteGroup.Owner");
            }
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage());
        }
    }


    @Override
    public List<Project> listProjects(Integer groupId, String username) {
        GitLabApi gitLabApi = gitlab4jclient.getGitLabApi(username);
        try {
            return gitLabApi.getGroupApi().getProjects(groupId);
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage());
        }
    }

    @Override
    public Group queryGroupByName(String groupName) {
        GitLabApi gitLabApi = gitlab4jclient.getGitLabApi(null);
        try {
            return gitLabApi.getGroupApi().getGroup(groupName);
        } catch (GitLabApiException e) {
            return null;
        }
    }

    @Override
    public List<Group> listGroups() {
        GitLabApi gitLabApi = gitlab4jclient.getGitLabApi(null);
        try {
            return gitLabApi.getGroupApi().getGroups();
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage());
        }
    }

    @Override
    public List<Member> listMember(Integer groupId) {
        GitLabApi gitLabApi = gitlab4jclient.getGitLabApi();
        try {
            return gitLabApi.getGroupApi().getMembers(groupId);
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage());
        }
    }

    @Override
    public Member queryMemberByUserId(Integer groupId, Integer userId) {
        GitLabApi gitLabApi = gitlab4jclient.getGitLabApi();
        try {
            return gitLabApi.getGroupApi().getMember(groupId, userId);
        } catch (GitLabApiException e) {
            return new Member();
        }
    }

    @Override
    public Member createMember(Integer groupId, MemberDto member) {
        GitLabApi gitLabApi = gitlab4jclient.getGitLabApi();
        try {
            return gitLabApi.getGroupApi().addMember(groupId, member.getUserId(), member.getAccessLevel(),
                    member.getExpiresAt());
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage());
        }
    }

    @Override
    public Member updateMember(Integer groupId, MemberDto member) {
        GitLabApi gitLabApi = gitlab4jclient.getGitLabApi();
        try {
            return gitLabApi.getGroupApi().updateMember(groupId, member.getUserId(), member.getAccessLevel(),
                    member.getExpiresAt());
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage());
        }
    }

    @Override
    public void deleteMember(Integer groupId, Integer userId) {
        GitLabApi gitLabApi = gitlab4jclient.getGitLabApi();
        try {
            gitLabApi.getGroupApi().removeMember(groupId, userId);
        } catch (GitLabApiException e) {
            throw new CommonException(e.getMessage());
        }
    }
}