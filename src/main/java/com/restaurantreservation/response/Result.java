package com.restaurantreservation.response;

import lombok.Value;

@Value
public class Result<D> {

    ResponseStatus status;
    int httpCode;

    D data;

//    public static <T extends BaseMessage> Result<T, D> message(T message, ) {
//        return new Result<>(message, data);
//    }

    private Result(ResponseStatus status, int httpCode, D data) {
        this.status = status;
        this.httpCode = httpCode;
        this.data = data;
    }
}
