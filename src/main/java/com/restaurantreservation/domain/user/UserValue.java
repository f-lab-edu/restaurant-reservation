package com.restaurantreservation.domain.user;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserValue {

    private String email;
    private String password;
    private String name;
    private String phoneNumber;
    private UserType userType;
    private UserStatus userStatus;


}

