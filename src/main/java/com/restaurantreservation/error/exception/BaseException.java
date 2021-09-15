package com.restaurantreservation.error.exception;

import com.restaurantreservation.error.message.BaseExceptionMessage;
import lombok.Getter;

public class BaseException extends RuntimeException {

    @Getter
    private final BaseExceptionMessage baseExceptionMessage;

    public BaseException(BaseExceptionMessage e) {
        super(e.getErrorMessage());
        this.baseExceptionMessage = e;
    }
}
