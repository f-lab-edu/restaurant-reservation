package com.restaurantreservation.controller;

import com.restaurantreservation.domain.user.UserValue;
import com.restaurantreservation.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        userService.userLogin(userValue);

        return Result.createStatusAndMessage(200, "로그인 성공");
    }

}
