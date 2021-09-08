package com.restaurantreservation.response;

public interface BaseResponse {
    ResponseStatus getStatus();
    int getHttpCode();
    String getMessage();
}
