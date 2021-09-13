package com.restaurantreservation.response;

import lombok.Value;

@Value
public class RuntimeExceptionResponse implements BaseExceptionResponse {

    ResponseStatus status;
    int httpCode;
    String errorMessage;

    public static RuntimeExceptionResponse of(RuntimeException e) {
        return new RuntimeExceptionResponse(ResponseStatus.FAIL, 500, e.getLocalizedMessage());
    }

    private RuntimeExceptionResponse(ResponseStatus status, int httpCode, String errorMessage) {
        this.status = status;
        this.httpCode = httpCode;
        this.errorMessage = errorMessage;
    }
}
