package com.restaurantreservation.error.exception.user;

import com.restaurantreservation.error.message.user.UserExceptionMessage;
import lombok.Getter;

public class UserException extends RuntimeException {

    @Getter
    private final UserExceptionMessage userExceptionMessage;

    public UserException(UserExceptionMessage userJoinExceptionMessage) {
        super(userJoinExceptionMessage.getErrorMessage());
        this.userExceptionMessage = userJoinExceptionMessage;
    }
}