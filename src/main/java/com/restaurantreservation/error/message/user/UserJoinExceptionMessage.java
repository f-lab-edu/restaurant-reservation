package com.restaurantreservation.error.message.user;

import com.restaurantreservation.error.message.BaseExceptionMessage;
import com.restaurantreservation.response.ResponseStatus;
import lombok.Getter;

import static com.restaurantreservation.response.ResponseStatus.*;

@Getter
public enum UserJoinExceptionMessage implements BaseExceptionMessage {

    WRONG_EMAIL(FAIL, 600, "아이디 형식이 잘못되었습니다."),
    DUPLICATED_EMAIL(FAIL, 601, "Email 이 이미 사용중입니다."),
    WRONG_PASSWORD_FORM(FAIL, 600, "패스워드 형식이 잘못되었습니다."),
    STOPPED_EMAIL(FAIL, 602, "정지된 Email 입니다."),
    UNKNOWN_FAIL(FAIL, 500, "알 수 없는 오류로 회원 저장에 실패하였습니다.");

    private final ResponseStatus status;
    private final int httpStatus;
    private final String errorMessage;

    UserJoinExceptionMessage(ResponseStatus status, int httpStatus, String errorMessage) {
        this.status = status;
        this.httpStatus = httpStatus;
        this.errorMessage = errorMessage;
    }
}
