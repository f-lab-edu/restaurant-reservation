package com.restaurantreservation.service;

import com.restaurantreservation.domain.user.UserEntity;
import com.restaurantreservation.domain.user.UserStatus;
import com.restaurantreservation.domain.user.UserValue;
import com.restaurantreservation.error.exception.user.UserJoinException;
import com.restaurantreservation.error.message.user.UserJoinExceptionMessage;
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
    public void userSave(UserValue userValue) {

        //이미 저장된 아이디(email) 인지 체크
        emailDuplicateCheck(userValue);

        UserEntity userEntity = UserEntity.create(userValue);
        userRepository.save(userEntity);
    }

    /**
     * 활동 상태의 email 중복되면 email 중복에러 return
     */
    private void emailDuplicateCheck(UserValue userValue) {
        if (userRepository.existsByEmailAndUserStatus(userValue.getEmail(), UserStatus.ACTIVE)) {
            throw new UserJoinException(UserJoinExceptionMessage.DUPLICATED_EMAIL);
        }
    }
}
