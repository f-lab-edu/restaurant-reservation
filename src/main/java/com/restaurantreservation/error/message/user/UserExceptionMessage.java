package com.restaurantreservation.error.message.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.restaurantreservation.error.message.BaseExceptionMessage;
import lombok.Getter;

/**
 * userException 이 터졌을때 보내는 message 들
 *
 * JsonFormat - test code 작성을 위해 추가
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum UserExceptionMessage implements BaseExceptionMessage {

    // 회원가입 에러 메세지
    WRONG_EMAIL(600, "아이디 형식이 잘못되었습니다."),
    DUPLICATED_EMAIL(601, "Email 이 이미 사용중입니다."),
    WRONG_PASSWORD_FORM(600, "패스워드 형식이 잘못되었습니다."),
    STOPPED_EMAIL(602, "정지된 Email 입니다."),
    WRONG_USER_TYPE(600, "회원 타입을 선택해 주세요"),
    UNKNOWN_FAIL(500, "알 수 없는 오류로 회원 저장에 실패하였습니다."),
    // 로그인 에러 메세지
    USER_NOT_FOUNT(600, "해당 유저가 존재하지 않습니다."),
    WRONG_PASSWORD(600, "비밀번호가 다릅니다."),

    // userHistory 에러 메세지
    HISTORY_SAVE_FAIL(500, "회원 history 저장에 실패하였습니다."),

    // 로그인 체크 에러 메세지
    NEED_LOGIN(606, "로그인이 필요합니다.");

    private final int code;
    private final String errorMessage;

    UserExceptionMessage(int code, String errorMessage) {
        this.code = code;
        this.errorMessage = errorMessage;
    }
}
