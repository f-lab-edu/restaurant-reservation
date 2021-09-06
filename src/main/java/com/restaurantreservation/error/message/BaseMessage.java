package com.restaurantreservation.error.message;

import com.restaurantreservation.response.ResponseStatus;

public interface BaseMessage {
    ResponseStatus getStatus();
    int getHttpCode();
    String getMessage();
}
