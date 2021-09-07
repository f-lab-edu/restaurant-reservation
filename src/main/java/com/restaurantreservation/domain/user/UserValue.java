package com.restaurantreservation.domain.user;


import com.restaurantreservation.error.exception.user.UserJoinException;
import com.restaurantreservation.error.message.user.UserJoinExceptionMessage;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.util.regex.Pattern;

/**
 *
 */
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
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


    public static void isValid(UserValue userValue) {

        boolean emailRegexCheck = Pattern.compile(EMAIL_REGEX)
                .matcher(userValue.getEmail())
                .matches();

//        boolean passwordRegexCheck = Pattern.compile(PASSWORD_REGEX)
//                .matcher(userValue.getPassword())
//                .matches();

        if (!emailRegexCheck) {
            throw new UserJoinException(UserJoinExceptionMessage.WRONG_EMAIL);
        }
//        if (!passwordRegexCheck) {
//            throw new UserJoinException(UserJoinExceptionMessage.WRONG_PASSWORD_FORM);
//        }
        if (userValue.getUserType() == null) {
            throw new UserJoinException(UserJoinExceptionMessage.WRONG_USER_TYPE);
        }
    }


}

