package com.restaurantreservation.service;

import com.restaurantreservation.domain.user.UserEntity;
import com.restaurantreservation.domain.user.UserValue;
import com.restaurantreservation.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    /**
     * 회원 저장 로직 - (아직 Exception 처리 전)
     */
    //**findById 등을 구현 후 return 값 변경 필요
    public UserEntity userSave(UserValue userValue) {

        UserEntity userEntity = UserEntity.create(userValue);

        return userRepository.save(userEntity);
    }
}
