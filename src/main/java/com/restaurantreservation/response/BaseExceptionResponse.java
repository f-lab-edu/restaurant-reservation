package com.restaurantreservation.response;

public interface BaseExceptionResponse {
    ResponseStatus getStatus();
    int getHttpCode();
    String getErrorMessage();
}
