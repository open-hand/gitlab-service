package io.choerodon.gitlab.app.service;

import java.util.List;

import io.choerodon.gitlab.api.vo.MileStoneVO;
import org.gitlab4j.api.models.Milestone;

public interface MileStoneService {
    /**
     * 创建milestone
     *
     * @param mileStoneVO milestone对象
     * @return Milestone
     */
    Milestone createMilestone(MileStoneVO mileStoneVO);

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
     * @param mileStoneVO mileStoneDto对象
     * @return Milestone
     */
    Milestone updateMilestone(MileStoneVO mileStoneVO);

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
     * @param mileStoneVO MileStoneDto对象
     * @return List
     */
    List<Milestone> listMileStoneByOptions(MileStoneVO mileStoneVO);


    /**
     * 查询单个mileStone信息
     *
     * @param projectId   项目id
     * @param milestoneId milestoneId
     * @return Milestone
     */
    Milestone queryMilestone(Integer projectId, Integer milestoneId);
}
