package com.restaurantreservation.domain.user;


import com.restaurantreservation.error.exception.user.UserException;
import com.restaurantreservation.error.message.user.UserExceptionMessage;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Pattern;

/**
 * Client 에서 User 정보를 받아오는 부분
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Slf4j
public class UserValue {

    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private UserType userType;

    /**
     * 회원 Valid check 하기 위한 로직
     */
    public static final String EMAIL_REGEX = "^(.+)@(.+)$";

    // 숫자, 영문(대소문자 구분) , 특수문자 추가 , 8~20글자 사이
    // 토이 프로젝트라 주석처리
//    public static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";


    /**
     * valid check 는 userEntity 로 변환전에 체크해서
     * check 에 실패하면 빨리 controller 에서
     * return 하려고 userValue 에서 처리하도록 구현하였습니다.
     */
    public static void isValid(UserValue userValue) {

        emailPatternCheck(userValue.getEmail());
        passwordPatternCheck(userValue.getPassword());

        if (userValue.getUserType() == null) {
            throw new UserException(UserExceptionMessage.WRONG_USER_TYPE);
        }
    }

    public static void isLoginValid(UserValue userValue) {
        emailPatternCheck(userValue.getEmail());
        passwordPatternCheck(userValue.getPassword());
    }

    private static void emailPatternCheck(String email) {
        boolean emailRegexCheck = Pattern.compile(EMAIL_REGEX)
                .matcher(email)
                .matches();

        if (!emailRegexCheck) {
            throw new UserException(UserExceptionMessage.WRONG_EMAIL);
        }
    }

    private static void passwordPatternCheck(String password) {
//        boolean passwordRegexCheck = Pattern.compile(PASSWORD_REGEX)
//                .matcher(password)
//                .matches();
//
//        if (!passwordRegexCheck) {
//            throw new UserException(UserExceptionMessage.WRONG_PASSWORD_FORM);
//        }
        if (password == null) {
            throw new UserException(UserExceptionMessage.WRONG_PASSWORD_FORM);
        }
    }

    /**
     * 필수값 email, 나머지는 선택값으로 builder 구현
     */
    public static class Builder {

        // 필수 인자
        private final String email;

        // 선택
        private UserType userType;
        private String password;
        private String name;
        private String phoneNumber;

        public Builder(String email) {
            this.email = email;
        }

        public Builder userType(UserType userType) {
            this.userType = userType;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public UserValue build() {
            return new UserValue(email, password, name, phoneNumber, userType);
        }
    }

    private UserValue(String email, String password, String name, String phoneNumber, UserType userType) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "UserValue{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", userType=" + userType +
                '}';
    }
}

