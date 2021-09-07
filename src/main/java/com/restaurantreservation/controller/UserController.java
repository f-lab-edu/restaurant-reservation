package com.restaurantreservation.controller;

import com.restaurantreservation.domain.user.UserValue;
import com.restaurantreservation.error.message.user.UserMessage;
import com.restaurantreservation.response.BaseResponse;
import com.restaurantreservation.response.user.UserResponse;
import com.restaurantreservation.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<? extends BaseResponse> userJoin(@RequestBody UserValue userValue) {
        //valid check
        UserValue.isValid(userValue);
        userService.userSave(userValue);

        return ResponseEntity.ok(UserResponse.of(UserMessage.JOIN_SUCCESS));
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<? extends BaseResponse> userFind(@PathVariable Long id) {
//
//        UserValue getUserValue = userService.findByUserId(id);
//
//
//    }


}
