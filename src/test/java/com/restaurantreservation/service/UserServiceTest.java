package com.restaurantreservation.service;

import com.restaurantreservation.domain.user.UserEntity;
import com.restaurantreservation.domain.user.UserStatus;
import com.restaurantreservation.domain.user.UserType;
import com.restaurantreservation.domain.user.UserValue;
import com.restaurantreservation.repository.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


//Mock 객체를 사용하기 위해 추가
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserService userJoinService;

    /**
     * user 회원가입 성공 테스트
     * 현재 패스워드 암호화, 예외 처리, Email 인증 부분 처리 추가 필요
     */
    @Test
    @DisplayName("유저 회원가입 성공 테스트")
    void userJoinTest() {

        final UserValue userValue = UserValue.builder()
                .email("test@app.com")
                .password("1234")
                .name("아무개")
                .phoneNumber("010-0000-0000")
                .userStatus(UserStatus.ACTIVE)
                .userType(UserType.CUSTOMER)
                .build();

        UserEntity testUserEntity = UserEntity.create(userValue);
        when(userRepository.save(any())).thenReturn(testUserEntity);

        //when
        UserEntity userEntity = userJoinService.userSave(userValue);

        //then
        verify(userRepository, times(1)).save(any());
        verify(userRepository, never()).findAll();
        verifyNoMoreInteractions(userRepository);
        assertThat(userEntity).isEqualTo(testUserEntity);

    }

}