package com.restaurantreservation.error.message.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.restaurantreservation.error.message.BaseExceptionMessage;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum JWTTokenExceptionMessage implements BaseExceptionMessage {

    UNSUPPORTED(400, "JWT 형식이 올바르지 않습니다."),
    MALFORMED(400, "JWT 구성이 올바르지 않습니다."),
    EXPIRED(400, "JWT 유효기간이 만료되었습니다."),
    ClAIM_FAIL(400, "JWT 권한 Claim 검사가 실패하였습니다."),
    FAIL(400, "JWT 인증이 실패하였습니다."),
    NULL(400, "token is null"),

    TOKEN_TIME_WRONG(600, "토큰 시간 에러"),

    REFRESH_TOKEN_NOT_EXISTS(600, "Refresh token 이 없습니다.")

    ;

    private final int code;
    private final String errorMessage;

    JWTTokenExceptionMessage(int code, String errorMessage) {
        this.code = code;
        this.errorMessage = errorMessage;
    }
}
