package com.restaurantreservation.error.message;

import com.restaurantreservation.response.ResponseStatus;

public interface BaseExceptionMessage {
    ResponseStatus getStatus();
    int getHttpStatus();
    String getErrorMessage();
}
