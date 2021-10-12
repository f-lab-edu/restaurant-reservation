package com.restaurantreservation.domain.user.login;

import lombok.Value;

@Value
public class TokenValue {
    long userId;
    String memberId;


    public static TokenValue create(long userId, String memberId) {
        return new TokenValue(userId, memberId);
    }

    private TokenValue(long userId, String memberId) {
        this.userId = userId;
        this.memberId = memberId;
    }
}
