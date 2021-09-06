package com.restaurantreservation.response;

public interface BaseExceptionResponse extends BaseResponse {
    ResponseStatus getStatus();
    int getHttpCode();
    String getErrorMessage();
}
