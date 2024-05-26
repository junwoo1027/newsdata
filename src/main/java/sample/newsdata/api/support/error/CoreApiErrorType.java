package sample.newsdata.api.support.error;

import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpStatus;

public enum CoreApiErrorType {

    DEFAULT_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, CoreApiErrorCode.E500, "An unexpected error has occurred.",
            LogLevel.ERROR),
    ALREADY_REGISTERED_KEYWORD(HttpStatus.INTERNAL_SERVER_ERROR, CoreApiErrorCode.E1000, "Already registered keyword.",
                  LogLevel.ERROR),
    AUTH_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, CoreApiErrorCode.E1001, "Auth failed.", LogLevel.ERROR),
    NOT_FOUND_TARGET(HttpStatus.INTERNAL_SERVER_ERROR, CoreApiErrorCode.E1002, "Not found target.", LogLevel.ERROR),
    INVALID_USER_INFO(HttpStatus.INTERNAL_SERVER_ERROR, CoreApiErrorCode.E1003, "Invalid user information.", LogLevel.ERROR),
    EXPIRED_REFRESH_TOKEN(HttpStatus.INTERNAL_SERVER_ERROR, CoreApiErrorCode.E1004, "Expired refresh token.", LogLevel.ERROR),
    NOT_FOUND_USER_TOKEN(HttpStatus.INTERNAL_SERVER_ERROR, CoreApiErrorCode.E1005, "Not found user token.", LogLevel.ERROR);
    private final HttpStatus status;

    private final CoreApiErrorCode code;

    private final String message;

    private final LogLevel logLevel;

    CoreApiErrorType(HttpStatus status, CoreApiErrorCode code, String message, LogLevel logLevel) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.logLevel = logLevel;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public CoreApiErrorCode getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

}
