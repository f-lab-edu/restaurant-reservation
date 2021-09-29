package com.restaurantreservation.error.exception.user;

import com.restaurantreservation.error.exception.BaseException;
import com.restaurantreservation.error.message.BaseExceptionMessage;

public class UserAuthException extends BaseException {
    public UserAuthException(BaseExceptionMessage e) {
        super(e);
    }
}
