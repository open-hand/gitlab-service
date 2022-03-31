package io.choerodon.gitlab.app.service;

import org.gitlab4j.api.models.Release;
import org.gitlab4j.api.models.ReleaseParams;

/**
 * 〈功能简述〉
 * 〈〉
 *
 * @author wanghao
 * @since 2022/3/31 16:03
 */
public interface ReleaseService {

    Release create(Integer projectId, Integer userId, ReleaseParams release);

    Release update(Integer projectId, Integer userId, ReleaseParams release);
}
