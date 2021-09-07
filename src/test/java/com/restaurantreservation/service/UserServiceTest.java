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
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


//Mock 객체를 사용하기 위해 추가
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    Encryption encryption;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userJoinService;

    /**
     * user 회원가입 성공 테스트
     * 현재 find 로직추가 해서 값을 비교하는 로직 필요 패스워드 Email 인증 부분 처리 추가 필요
     */
    @Test
    @DisplayName("유저 회원가입 성공 테스트")
    void canUserJoin() {


        final String password = "1234";
        final String salt = "11111111";


        final UserValue userValue = UserValue.builder()
                .email("test@app.com")
                .password(password)
                .name("아무개")
                .phoneNumber("010-0000-0000")
                .userType(UserType.CUSTOMER)
                .build();

        UserEntity testUserEntity = UserEntity.create(
                userValue.getEmail(),
                password,
                salt,
                userValue.getName(),
                userValue.getPhoneNumber(),
                userValue.getUserType());

        when(userRepository.save(any())).thenReturn(testUserEntity);

        //when
        userJoinService.userSave(userValue);

        //then
        verify(userRepository, times(1)).existsByEmailAndUserStatus("test@app.com", UserStatus.ACTIVE);
        verify(userRepository, times(1)).save(any());
        verifyNoMoreInteractions(userRepository);
    }
}