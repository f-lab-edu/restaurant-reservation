package com.restaurantreservation.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Value;

@Value
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> {

    int code;
    String message;
    T data;

    public static <T> Result<T> createAll(int code, String message, T data) {
        return new Result<>(code, message, data);
    }

    public static <T> Result<T> createStatusAndMessage(int code, String message) {
        return new Result<>(code, message, null);
    }

    public static <T> Result<T> createStatus(int code) {
        return new Result<>(code, null, null);
    }

    public static <T> Result<T> createErrorResult(int code, String message) {
        return new Result<>(code, message, null);
    }

    private Result(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}