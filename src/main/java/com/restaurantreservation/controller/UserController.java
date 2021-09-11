package com.restaurantreservation.controller;

import com.restaurantreservation.domain.user.UserValue;
import com.restaurantreservation.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<Object> userJoin(@RequestBody UserValue userValue) {
        //valid check
        UserValue.isValid(userValue);
        userService.userSaveAndUserHistorySave(userValue);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<? extends BaseResponse> userFind(@PathVariable Long id) {
//        UserValue getUserValue = userService.findByUserId(id);
//    }


}
