package com.restaurantreservation.error.message;

public interface BaseExceptionMessage {
    int getStatus();
    String getErrorMessage();
}
