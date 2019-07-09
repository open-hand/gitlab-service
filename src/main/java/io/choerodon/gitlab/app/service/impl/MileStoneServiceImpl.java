package io.choerodon.gitlab.app.service.impl;

import java.util.List;

import io.choerodon.gitlab.api.vo.MileStoneVO;
import org.gitlab4j.api.MileStonesApi;
import org.gitlab4j.api.models.Milestone;
import org.springframework.stereotype.Service;

import io.choerodon.core.exception.FeignException;
import io.choerodon.gitlab.app.service.MileStoneService;
import io.choerodon.gitlab.infra.common.client.Gitlab4jClient;


@Service
public class MileStoneServiceImpl implements MileStoneService {

    private Gitlab4jClient gitlab4jclient;

    public MileStoneServiceImpl(Gitlab4jClient gitlab4jclient) {
        this.gitlab4jclient = gitlab4jclient;
    }

    @Override
    public Milestone createMilestone(MileStoneVO mileStoneVO) {
        try {
            return gitlab4jclient
                    .getGitLabApi()
                    .getMileStonesApi()
                    .createMilestone(mileStoneVO.getProjectId(),
                            mileStoneVO.getTitle(),
                            mileStoneVO.getDescription(),
                            mileStoneVO.getDueDate(),
                            mileStoneVO.getStartDate());
        } catch (Exception e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public Milestone closeMilestone(Integer projectId, Integer milestoneId) {
        try {
            return gitlab4jclient
                    .getGitLabApi()
                    .getMileStonesApi()
                    .closeMilestone(projectId, milestoneId);
        } catch (Exception e) {
            throw new FeignException(e.getMessage(), e);

        }
    }

    @Override
    public Milestone activeMilestone(Integer projectId, Integer milestoneId) {
        try {
            return gitlab4jclient
                    .getGitLabApi()
                    .getMileStonesApi()
                    .activateMilestone(projectId, milestoneId);
        } catch (Exception e) {
            throw new FeignException(e.getMessage(), e);

        }
    }

    @Override
    public Milestone updateMilestone(MileStoneVO mileStoneVO) {
        try {
            return gitlab4jclient
                    .getGitLabApi()
                    .getMileStonesApi()
                    .updateMilestone(mileStoneVO.getProjectId(),
                            mileStoneVO.getMilestoneId(),
                            mileStoneVO.getTitle(),
                            mileStoneVO.getDescription(),
                            mileStoneVO.getDueDate(),
                            mileStoneVO.getStartDate(),
                            mileStoneVO.getMilestoneState());
        } catch (Exception e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public List<Milestone> listMilestones(Integer projectId, Integer page, Integer perPage) {
        try {
            MileStonesApi mileStonesApi = gitlab4jclient.getGitLabApi(null).getMileStonesApi();
            if (projectId != null) {
                return page == null || perPage == null
                        ? mileStonesApi.getMilestones(projectId)
                        : mileStonesApi.getMilestones(projectId, page, perPage);
            } else {
                throw new FeignException("error.milestones.query");
            }
        } catch (Exception e) {
            throw new FeignException(e.getMessage(), e);
        }

    }

    @Override
    public List<Milestone> listMileStoneByOptions(MileStoneVO mileStoneVO) {
        MileStonesApi mileStonesApi = gitlab4jclient.getGitLabApi(null).getMileStonesApi();
        try {
            if (mileStoneVO.getProjectId() != null) {
                if (mileStoneVO.getMilestoneState() != null) {
                    if (mileStoneVO.getSearch() != null) {
                        return mileStonesApi.getMilestones(
                                mileStoneVO.getProjectId(),
                                mileStoneVO.getMilestoneState(),
                                mileStoneVO.getSearch());
                    } else {
                        return mileStonesApi.getMilestones(
                                mileStoneVO.getProjectId(),
                                mileStoneVO.getMilestoneState());
                    }
                } else {
                    if (mileStoneVO.getSearch() != null) {
                        return mileStonesApi.getMilestones(
                                mileStoneVO.getProjectId(),
                                mileStoneVO.getSearch());
                    }
                }
                return listMilestones(mileStoneVO.getProjectId(), null, null);
            } else {
                throw new FeignException("error.milestones.query");
            }
        } catch (Exception e) {
            throw new FeignException(e.getMessage(), e);
        }
    }

    @Override
    public Milestone queryMilestone(Integer projectId, Integer milestoneId) {
        try {
            return gitlab4jclient
                    .getGitLabApi(null)
                    .getMileStonesApi()
                    .getMilestone(projectId, milestoneId);
        } catch (Exception e) {
            throw new FeignException(e.getMessage(), e);

        }
    }
}
