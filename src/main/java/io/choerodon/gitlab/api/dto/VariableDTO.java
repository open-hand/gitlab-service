package io.choerodon.gitlab.api.dto;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Creator: ChangpingShi0213@gmail.com
 * Date:  9:51 2019/3/12
 * Description:
 */
public class VariableDTO {
    @NotNull
    private List<String> keys;
    @NotNull
    private List<String> values;
    @NotNull
    private List<Boolean> protecteds;
    private Integer userId;

    public List<String> getKeys() {
        return keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public List<Boolean> getProtecteds() {
        return protecteds;
    }

    public void setProtecteds(List<Boolean> protecteds) {
        this.protecteds = protecteds;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
