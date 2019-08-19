package io.choerodon.gitlab.app.service.impl;

import java.util.List;

import org.gitlab4j.api.LabelsApi;
import org.gitlab4j.api.models.Label;
import org.springframework.stereotype.Service;

import io.choerodon.core.exception.FeignException;
import io.choerodon.gitlab.api.vo.LabelVO;
import io.choerodon.gitlab.app.service.LabelsService;
import io.choerodon.gitlab.infra.common.client.Gitlab4jClient;

@Service
public class LabelsServiceImpl implements LabelsService {

    private Gitlab4jClient gitlab4jclient;

    public LabelsServiceImpl(Gitlab4jClient gitlab4jclient) {
        this.gitlab4jclient = gitlab4jclient;
    }

    @Override
    public List<Label> listLabels(Integer projectId, Integer page, Integer perPage) {
        try {
            LabelsApi labelsApi = gitlab4jclient
                    .getGitLabApi(null)
                    .getLabelsApi();
            if (projectId != null) {
                return page == null || perPage == null
                        ? labelsApi.getLabels(projectId)
                        : labelsApi.getLabels(projectId, page, perPage);
            } else {
                throw new FeignException("error.label.query");
            }
        } catch (Exception e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public Label createLabel(LabelVO labelVO) {
        try {
            return gitlab4jclient
                    .getGitLabApi(null)
                    .getLabelsApi()
                    .createLabel(labelVO.getProjectId(),
                            labelVO.getName(),
                            labelVO.getColor(),
                            labelVO.getDescription(),
                            labelVO.getPriority());
        } catch (Exception e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public Label updateLabel(LabelVO labelVO) {
        try {
            return gitlab4jclient
                    .getGitLabApi(null)
                    .getLabelsApi()
                    .updateLabel(labelVO.getProjectId(),
                            labelVO.getName(),
                            labelVO.getNewName(),
                            labelVO.getColor(),
                            labelVO.getDescription(),
                            labelVO.getPriority());
        } catch (Exception e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public void deleteLabel(Integer projectId, String name) {
        try {
            gitlab4jclient
                    .getGitLabApi(null)
                    .getLabelsApi()
                    .deleteLabel(projectId, name);
        } catch (Exception e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public Label subscribeLabel(Integer projectId, Integer labelId) {
        try {
            return gitlab4jclient
                    .getGitLabApi(null)
                    .getLabelsApi()
                    .subscribeLabel(projectId, labelId);
        } catch (Exception e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public Label unSubscribeLabel(Integer projectId, Integer labelId) {
        try {
            return gitlab4jclient
                    .getGitLabApi(null)
                    .getLabelsApi()
                    .unsubscribeLabel(projectId, labelId);
        } catch (Exception e) {
            throw new FeignException(e.getMessage(), e);
        }
    }
}
