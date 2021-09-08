package com.restaurantreservation.response.message;

import com.restaurantreservation.response.ResponseStatus;

public interface BaseMessage {
    ResponseStatus getStatus();
    int getHttpCode();
    String getMessage();
}
