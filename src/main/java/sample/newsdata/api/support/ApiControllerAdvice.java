package sample.newsdata.api.support;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sample.newsdata.api.support.error.CoreApiErrorType;
import sample.newsdata.api.support.error.CoreApiException;
import sample.newsdata.api.support.response.ApiResponse;

@RestControllerAdvice
public class ApiControllerAdvice {

    @ExceptionHandler(CoreApiException.class)
    public ResponseEntity<ApiResponse<?>> handleCoreApiException(CoreApiException e) {
        return new ResponseEntity<>(ApiResponse.error(e.getErrorType(), e.getData()), e.getErrorType().getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleException(Exception e) {
        return new ResponseEntity<>(ApiResponse.error(CoreApiErrorType.DEFAULT_ERROR),
                CoreApiErrorType.DEFAULT_ERROR.getStatus());
    }

}
