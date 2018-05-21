package io.choerodon.gitlab.app.service;

import java.util.List;

import org.gitlab4j.api.models.Milestone;

import io.choerodon.gitlab.api.dto.MileStoneDto;

public interface MileStoneService {
    /**
     * 创建milestone
     *
     * @param mileStoneDto milestone对象
     * @return Milestone
     */
    Milestone createMilestone(MileStoneDto mileStoneDto);

    /**
     * 关闭milestone
     *
     * @param projectId   项目id
     * @param milestoneId milestoneId
     * @return Milestone
     */
    Milestone closeMilestone(Integer projectId, Integer milestoneId);

    /**
     * 激活milestone
     *
     * @param projectId   项目id
     * @param milestoneId milestoneId
     * @return Milestone
     */
    Milestone activeMilestone(Integer projectId, Integer milestoneId);

    /**
     * 更新milestone
     *
     * @param mileStoneDto mileStoneDto对象
     * @return Milestone
     */
    Milestone updateMilestone(MileStoneDto mileStoneDto);

    /**
     * 查询milestones列表
     *
     * @param page      页码
     * @param perPage   每页大小
     * @param projectId 项目Id
     * @return List
     */
    List<Milestone> listMilestones(Integer projectId, Integer page, Integer perPage);

    /**
     * 通过projectId,milestoneState和search查询milestones列表
     *
     * @param mileStoneDto MileStoneDto对象
     * @return List
     */
    List<Milestone> listMileStoneByOptions(MileStoneDto mileStoneDto);


    /**
     * 查询单个mileStone信息
     *
     * @param projectId   项目id
     * @param milestoneId milestoneId
     * @return Milestone
     */
    Milestone queryMilestone(Integer projectId, Integer milestoneId);
}
