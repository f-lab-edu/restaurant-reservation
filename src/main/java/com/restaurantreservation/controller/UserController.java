package com.restaurantreservation.controller;

import com.restaurantreservation.domain.user.UserValue;
import com.restaurantreservation.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public Result userLogin(@RequestBody UserValue userValue) {
        //login valid check
        UserValue.isLoginValid(userValue);
        userService.loginUser(userValue);

        return Result.createStatusAndMessage(200, "로그인 성공");
    }

    @GetMapping("/health-check")
    public String healthCheck(){
        return "health_ok";
    }
}
