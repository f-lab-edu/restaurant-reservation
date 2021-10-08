package com.restaurantreservation.service;

import com.restaurantreservation.repository.user.LoginAuthRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserAuthService {

    private final LoginAuthRepository loginAuthRepository;

}
