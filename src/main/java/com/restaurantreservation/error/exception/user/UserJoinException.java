package com.restaurantreservation.error.exception.user;

import com.restaurantreservation.error.message.user.UserJoinExceptionMessage;
import lombok.Getter;

public class UserJoinException extends RuntimeException {

    @Getter
    private final UserJoinExceptionMessage userJoinExceptionMessage;

    public static UserJoinException of(UserJoinExceptionMessage userJoinExceptionMessage) {
        return new UserJoinException(userJoinExceptionMessage);
    }

    public UserJoinException(UserJoinExceptionMessage userJoinExceptionMessage) {
        super(userJoinExceptionMessage.getErrorMessage());
        this.userJoinExceptionMessage = userJoinExceptionMessage;
    }
}
