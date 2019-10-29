package io.choerodon.gitlab.infra.common.exception;

import io.choerodon.core.exception.ExceptionResponse;
import io.choerodon.core.oauth.CustomUserDetails;
import io.choerodon.core.oauth.DetailsHelper;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Locale;

@ControllerAdvice
public class GitlabExceptionHandler {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(GitlabExceptionHandler.class);

    @Autowired
    MessageSource messageSource;

    /**
     * 合并请求不存在异常
     */
    @ExceptionHandler(MergeRequestNotFoundException.class)
    public ResponseEntity<ExceptionResponse> mergeRequestNotFoundException(MergeRequestNotFoundException e) {
        LOGGER.info("Merge request not fount");
        String message = null;
        try {
            message = messageSource.getMessage(e.getCode(), e.getParameters(), locale());
        } catch (Exception exception) {
            LOGGER.trace("exception message {}", exception.getMessage());
        }
        return new ResponseEntity<>(new ExceptionResponse(true, e.getCode(), message != null ? message : e.getTrace()), HttpStatus.NOT_FOUND);
    }

    /**
     * 返回用户的语言类型
     *
     * @return Locale
     */
    private Locale locale() {
        CustomUserDetails details = DetailsHelper.getUserDetails();
        Locale locale = Locale.SIMPLIFIED_CHINESE;
        if (details != null && "en_US".equals(details.getLanguage())) {
            locale = Locale.US;
        }
        return locale;
    }
}
