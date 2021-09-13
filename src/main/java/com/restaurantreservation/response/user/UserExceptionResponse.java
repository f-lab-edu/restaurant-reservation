package com.restaurantreservation.response.user;

import com.restaurantreservation.error.message.user.UserExceptionMessage;
import com.restaurantreservation.response.BaseExceptionResponse;
import com.restaurantreservation.response.ResponseStatus;
import lombok.Value;

@Value
public class UserExceptionResponse implements BaseExceptionResponse {

    ResponseStatus status;
    int httpCode;
    String errorMessage;
    // client 에서 처리한다면 error type 을 알아야 처리할 수 있을 것 같아서 추가하였습니다.
    String errorType;

    public static UserExceptionResponse of(UserExceptionMessage e) {
        return new UserExceptionResponse(e.getStatus(), e.getHttpCode(), e.getErrorMessage(), e.name());
    }

    private UserExceptionResponse(ResponseStatus status, int httpCode, String errorMessage, String errorType) {
        this.status = status;
        this.httpCode = httpCode;
        this.errorMessage = errorMessage;
        this.errorType = errorType;
    }
}