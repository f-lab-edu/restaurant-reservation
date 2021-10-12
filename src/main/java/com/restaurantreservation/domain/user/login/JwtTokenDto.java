package com.restaurantreservation.domain.user.login;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class JwtTokenDto {

    private String accessToken;
    private String refreshToken;


    public static JwtTokenDto create(String accessToken, String refreshToken) {
        return new JwtTokenDto(accessToken, refreshToken);
    }

    public static JwtTokenDto createAccessToken(String accessToken) {
        return new JwtTokenDto(accessToken);
    }


    private JwtTokenDto(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    private JwtTokenDto(String accessToken) {
        this.accessToken = accessToken;
    }

    protected JwtTokenDto() {
    }
}
