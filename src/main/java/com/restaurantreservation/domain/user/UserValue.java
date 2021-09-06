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
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Slf4j
public class UserValue {

    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private UserType userType;
    private UserStatus userStatus;

    public static final String EMAIL_REGEX = "^(.+)@(.+)$";
    public static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";


    public static void validCheck(UserValue userValue) {

        boolean emailRegexCheck = Pattern.compile(EMAIL_REGEX)
                .matcher(userValue.getEmail())
                .matches();

        //비밀번호 valid check 는 한번 동작확인하고 막을 계획입니다.
        boolean passwordRegexCheck = Pattern.compile(PASSWORD_REGEX)
                .matcher(userValue.getPassword())
                .matches();

        if (!emailRegexCheck) {
            throw new UserJoinException(UserJoinExceptionMessage.WRONG_EMAIL);
        }
        if (!passwordRegexCheck) {
            throw new UserJoinException(UserJoinExceptionMessage.WRONG_PASSWORD_FORM);
        }
        if (userValue.getUserType() == null) {
            throw new UserJoinException(UserJoinExceptionMessage.WRONG_USER_TYPE);
        }
    }
}

