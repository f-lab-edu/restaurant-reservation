package com.restaurantreservation.service;

import com.restaurantreservation.domain.user.UserEntity;
import com.restaurantreservation.domain.user.UserStatus;
import com.restaurantreservation.domain.user.UserValue;
import com.restaurantreservation.encrypt.Encryption;
import com.restaurantreservation.error.exception.user.UserException;
import com.restaurantreservation.error.message.user.UserExceptionMessage;
import com.restaurantreservation.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final Encryption encryption;

    /**
     * 회원 저장 로직 - (아직 Exception 처리 전)
     */
    public void userSave(UserValue userValue) {

        //이미 저장된 아이디(email) 인지 체크
        emailDuplicateCheck(userValue);

        //패스워드 암호화
        String salt = encryption.createSALT();
        String encryptedPassword = encryption.encrypt(userValue.getPassword(), salt);

        UserEntity userEntity = UserEntity.create(
                userValue.getEmail(),
                encryptedPassword,
                salt,
                userValue.getName(),
                userValue.getPhoneNumber(),
                userValue.getUserType());

        userRepository.save(userEntity);
    }

    public UserValue findByUserId(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(
                () -> new UserException(UserExceptionMessage.USER_NOT_FOUNT)
        );
        UserValue userValue = new UserValue.Builder(userEntity.getEmail())
                .name(userEntity.getName())
                .phoneNumber(userEntity.getPhoneNumber())
                .userType(userEntity.getUserType())
                .build();

        return userValue;
    }


    /**
     * 활동 상태의 email 중복되면 email 중복에러 return
     */
    private void emailDuplicateCheck(UserValue userValue) {
        if (userRepository.existsByEmailAndUserStatus(userValue.getEmail(), UserStatus.ACTIVE)) {
            throw new UserException(UserExceptionMessage.DUPLICATED_EMAIL);
        }
    }

}
