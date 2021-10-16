package com.restaurantreservation.controller;

import com.restaurantreservation.domain.user.UserValue;
import com.restaurantreservation.domain.user.login.JwtTokenDto;
import com.restaurantreservation.domain.user.login.JwtType;
import com.restaurantreservation.error.exception.user.UserException;
import com.restaurantreservation.error.message.user.JWTTokenExceptionMessage;
import com.restaurantreservation.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public Result userJoin(@RequestBody UserValue userValue) {
        //valid check
        UserValue.isValid(userValue);
        userService.userSaveAndUserHistorySave(userValue);

        return Result.createStatus(201);
    }

    @PostMapping("/login")
    public Result<JwtTokenDto> userLogin(@RequestBody UserValue userValue) {
        //login valid check
        UserValue.isLoginValid(userValue);
        JwtTokenDto jwtTokenDto = userService.loginUser(userValue);

        return Result.createAll(200, "로그인 성공", jwtTokenDto);
    }

    @PostMapping("/access_token/reissue")
    public Result<JwtTokenDto> reissueAccessToken(HttpServletRequest request) {
        String refreshToken = request.getHeader(JwtType.REFRESH_TOKEN.name());
        //refresh token 유효한지 확인 후
        //access token 재발급
        if (refreshToken == null) {
            throw new UserException(JWTTokenExceptionMessage.NULL);
        }
        JwtTokenDto jwtTokenDto = userService.reissueAccessToken(refreshToken);
        return Result.createAll(201, "Access token 재발급 성공", jwtTokenDto);
    }

    @GetMapping("/health-check")
    public String healthCheck() {
        return "health_ok";
    }
}
