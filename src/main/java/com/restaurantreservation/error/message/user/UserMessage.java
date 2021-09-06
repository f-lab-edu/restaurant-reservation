package com.restaurantreservation.error.message.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.restaurantreservation.error.message.BaseMessage;
import com.restaurantreservation.response.ResponseStatus;
import lombok.Getter;

import static com.restaurantreservation.response.ResponseStatus.*;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum UserMessage implements BaseMessage {

    JOIN_SUCCESS(SUCCESS, 201, "회원가입 성공");

    private final ResponseStatus status;
    private final int httpCode;
    private final String message;

    UserMessage(ResponseStatus status, int httpStatus, String message) {
        this.status = status;
        this.httpCode = httpStatus;
        this.message = message;
    }
}
