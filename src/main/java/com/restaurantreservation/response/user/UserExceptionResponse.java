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

    public static UserExceptionResponse of(UserExceptionMessage e) {
        return new UserExceptionResponse(e.getStatus(), e.getHttpCode(), e.getErrorMessage());
    }

    private UserExceptionResponse(ResponseStatus status, int httpCode, String errorMessage) {
        this.status = status;
        this.httpCode = httpCode;
        this.errorMessage = errorMessage;
    }
}