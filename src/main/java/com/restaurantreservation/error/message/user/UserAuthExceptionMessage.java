package com.restaurantreservation.error.message.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.restaurantreservation.error.message.BaseExceptionMessage;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum UserAuthExceptionMessage implements BaseExceptionMessage {

    TOKEN_TIME_WRONG(600, "토큰 시간 에러");

    private final int code;
    private final String errorMessage;

    UserAuthExceptionMessage(int code, String errorMessage) {
        this.code = code;
        this.errorMessage = errorMessage;
    }
}
