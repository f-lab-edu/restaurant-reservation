package com.restaurantreservation.service;

import com.restaurantreservation.domain.user.UserEntity;
import com.restaurantreservation.domain.user.UserStatus;
import com.restaurantreservation.domain.user.UserType;
import com.restaurantreservation.domain.user.UserValue;
import com.restaurantreservation.encrypt.Encryption;
import com.restaurantreservation.repository.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


//Mock 객체를 사용하기 위해 추가
//@ExtendWith(MockitoExtension.class)
class UserServiceTest {

//    @Mock
//    Encryption encryption;

//    @Mock
//    UserRepository userRepository;

//    @InjectMocks
//    UserService userJoinService;

    /**
     * user 회원가입 성공 테스트
     * Email 인증 부분 처리 추가 필요
     *
     * 현재 서비스 테스트 로직이 정확하지 않음 나중에 수정 필수
     *
     */
//    @Test
//    @DisplayName("유저 회원가입 성공 테스트")
//    void canUserJoin() {
//
//
//        final String password = "1234";
//        final String salt = "11111111";
//
//        final String testUserEmail = "test@app.com";
//
//        final UserValue userValue = new UserValue.Builder(testUserEmail)
//                .password(password)
//                .name("아무개")
//                .phoneNumber("010-0000-0000")
//                .userType(UserType.CUSTOMER)
//                .build();
//
//        UserEntity testUserEntity = UserEntity.create(
//                userValue.getEmail(),
//                password,
//                salt,
//                userValue.getName(),
//                userValue.getPhoneNumber(),
//                userValue.getUserType());
//
//        given(userRepository.save(any())).willReturn(testUserEntity);
//        given(userRepository.findById(testUserEmail)).willReturn(Optional.of(testUserEntity));
//        when(userRepository.save(any())).thenReturn(testUserEntity);
//
//        when
//        userJoinService.userSave(userValue);
//        UserEntity userEntity = userRepository.findById(testUserEmail).get();
//
//        then
//
//        verify(userRepository, times(1)).existsByEmailAndUserStatus(testUserEmail, UserStatus.ACTIVE);
//        verify(userRepository, times(1)).save(any());
//        verify(userRepository, times(1)).findById(testUserEmail);
//        verifyNoMoreInteractions(userRepository);
//
//        assertThat(userEntity.getEmail()).isEqualTo(testUserEntity.getEmail());
//        assertThat(userEntity.getName()).isEqualTo(testUserEntity.getName());
//        assertThat(userEntity.getUserType()).isEqualTo(testUserEntity.getUserType());
//        assertThat(userEntity.getPhoneNumber()).isEqualTo(testUserEntity.getPhoneNumber());
//
//        assertThat(userEntity.getSalt()).isEqualTo(testUserEntity.getSalt());
//        assertThat(userEntity.getPassword()).isEqualTo(testUserEntity.getPassword());
//
//        assertThat(userEntity).isNotEqualTo(testUserEntity);
//    }
}