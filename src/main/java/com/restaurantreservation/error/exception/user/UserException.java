package com.restaurantreservation.error.exception.user;

import com.restaurantreservation.error.exception.BaseException;
import com.restaurantreservation.error.message.BaseExceptionMessage;

public class UserException extends BaseException {

    public UserException(BaseExceptionMessage e) {
        super(e);
    }
}
