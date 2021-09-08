package com.restaurantreservation.response.message;

import com.restaurantreservation.response.ResponseStatus;

public interface BaseExceptionMessage {
    ResponseStatus getStatus();
    int getHttpCode();
    String getErrorMessage();
}
