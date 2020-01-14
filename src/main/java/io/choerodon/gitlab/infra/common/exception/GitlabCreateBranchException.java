package io.choerodon.gitlab.infra.common.exception;

/**
 * User: Mr.Wang
 * Date: 2020/1/14
 */
public class GitlabCreateBranchException extends RuntimeException {

    private static final long serialVersionUID = -6112780192479692859L;

    private String code;
    private String traceMessage;

    public GitlabCreateBranchException(String traceMessage, String code) {
        super(traceMessage);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTraceMessage() {
        return traceMessage;
    }

    public void setTraceMessage(String traceMessage) {
        this.traceMessage = traceMessage;
    }
}
