package io.choerodon.gitlab.infra.common.exception;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class MergeRequestNotFoundException extends RuntimeException {

    private final transient Object[] parameters;

    private String code;
    private String traceMessage;

    /**
     * 构造器
     *
     * @param code       异常code
     * @param parameters parameters
     */
    public MergeRequestNotFoundException(String code, Object... parameters) {
        super(code);
        this.parameters = parameters;
        this.code = code;
    }

    public MergeRequestNotFoundException(String code, Throwable cause, Object... parameters) {
        super(code, cause);
        this.parameters = parameters;
        this.code = code;
    }

    public MergeRequestNotFoundException(String code, Throwable cause) {
        super(code, cause);
        this.code = code;
        this.parameters = new Object[]{};
    }


    public MergeRequestNotFoundException(Throwable cause, Object... parameters) {
        super(cause);
        this.parameters = parameters;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public String getCode() {
        return code;
    }

    public String getTrace() {
        if (this.traceMessage == null) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            PrintStream ps = new PrintStream(outputStream);
            this.printStackTrace(ps);
            ps.flush();
            this.traceMessage = new String(outputStream.toByteArray());
        }
        return traceMessage;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> map = new LinkedHashMap<>();
        map.put("code", code);
        map.put("message", super.getMessage());
        return map;
    }
}
