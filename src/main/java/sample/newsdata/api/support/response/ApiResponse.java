package sample.newsdata.api.support.response;

import sample.newsdata.api.support.error.CoreApiErrorMessage;
import sample.newsdata.api.support.error.CoreApiErrorType;

public class ApiResponse<S> {

    private final ResultType result;

    private final S data;

    private final CoreApiErrorMessage error;

    private ApiResponse(ResultType result, S data, CoreApiErrorMessage error) {
        this.result = result;
        this.data = data;
        this.error = error;
    }

    public static ApiResponse<?> success() {
        return new ApiResponse<>(ResultType.SUCCESS, null, null);
    }

    public static <S> ApiResponse<S> success(S data) {
        return new ApiResponse<>(ResultType.SUCCESS, data, null);
    }

    public static ApiResponse<?> error(CoreApiErrorType error) {
        return new ApiResponse<>(ResultType.ERROR, null, new CoreApiErrorMessage(error));
    }

    public static ApiResponse<?> error(CoreApiErrorType error, Object errorData) {
        return new ApiResponse<>(ResultType.ERROR, null, new CoreApiErrorMessage(error, errorData));
    }

    public ResultType getResult() {
        return result;
    }

    public Object getData() {
        return data;
    }

    public CoreApiErrorMessage getError() {
        return error;
    }

}
