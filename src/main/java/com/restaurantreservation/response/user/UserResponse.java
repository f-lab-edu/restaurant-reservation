package com.restaurantreservation.response.user;

import com.restaurantreservation.response.message.user.UserMessage;
import com.restaurantreservation.response.BaseResponse;
import com.restaurantreservation.response.ResponseStatus;
import lombok.Value;

@Value
public class UserResponse implements BaseResponse {

    ResponseStatus status;
    int httpCode;
    String message;

    public static UserResponse of(UserMessage e) {
        return new UserResponse(e.getStatus(), e.getHttpCode(), e.getMessage());
    }

    private UserResponse(ResponseStatus status, int httpCode, String message) {
        this.status = status;
        this.httpCode = httpCode;
        this.message = message;
    }

}
