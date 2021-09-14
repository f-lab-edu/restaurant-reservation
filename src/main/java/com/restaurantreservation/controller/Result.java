package com.restaurantreservation.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Value;

@Value
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> {

    int status;
    String message;
    T data;

    public static <T> Result<T> createAll(int status, String message, T data) {
        return new Result<>(status, message, data);
    }

    public static <T> Result<T> createStatusAndMessage(int status, String message) {
        return new Result<>(status, message, null);
    }

    public static <T> Result<T> createStatus(int status) {
        return new Result<>(status, null, null);
    }

    public static <T> Result<T> createErrorResult(int status, String message) {
        return new Result<>(status, message, null);
    }

    private Result(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}