package com.restaurantreservation.service;

import com.restaurantreservation.domain.history.UserStatusHistory;
import com.restaurantreservation.domain.user.UserEntity;
import com.restaurantreservation.domain.user.UserStatus;
import com.restaurantreservation.domain.user.UserType;
import com.restaurantreservation.domain.user.UserValue;
import com.restaurantreservation.encrypt.Encryption;
import com.restaurantreservation.error.exception.user.UserException;
import com.restaurantreservation.error.message.user.UserExceptionMessage;
import com.restaurantreservation.repository.history.UserStatusHistoryRepository;
import com.restaurantreservation.repository.user.LoginAuthRepository;
import com.restaurantreservation.repository.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

//Mock 객체를 사용하기 위해 추가
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    LoginAuthRepository loginAuthRepository;

    @Mock
    UserRepository userRepository;

    @Mock
    Encryption encryption;

    @Mock
    UserStatusHistoryRepository userStatusHistoryRepository;

    @InjectMocks
    UserService userJoinService;


    final String password = "1234";
    final String salt = "11111111";

    final String testUserEmail = "test@app.com";

    final UserValue createUserValue() {
        return new UserValue.Builder(testUserEmail)
                .password(password)
                .name("아무개")
                .phoneNumber("010-0000-0000")
                .userType(UserType.CUSTOMER)
                .build();
    }

    UserEntity testUserEntity(UserValue userValue) {
        return UserEntity.create(
                userValue.getEmail(),
                password,
                salt,
                userValue.getName(),
                userValue.getPhoneNumber(),
                userValue.getUserType());
    }

    UserStatusHistory testUserSaveHistory(Long userId) {
        return UserStatusHistory.of(userId, UserStatus.ACTIVE);
    }

    /**
     * user 회원가입 테스트 시작
     * Email 인증 부분 처리 추가 필요
     */
    @Test
    @DisplayName("유저 회원가입 테스트- 성공")
    void canUserJoin() {
        UserValue userValue = createUserValue();
        UserEntity userEntity = testUserEntity(userValue);

        given(userRepository.save(any(UserEntity.class))).willReturn(userEntity);
        userJoinService.userSave(userValue);

        // 어떠한 쿼리가 몇번 실행되었는지 확인. - repository
        // 쓸데없는 쿼리가 나가지 않도록 예방, 쿼리 나가는 갯수 예측하기 위해 테스트하고있습니다.
        verify(userRepository, times(1)).existsByEmailAndUserStatus(testUserEmail, UserStatus.ACTIVE);
        verify(userRepository, times(1)).save(any(UserEntity.class));
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    @DisplayName("유저 회원가입 테스트 - 실패")
    void cannotUserJoin() {
        UserValue userValue = createUserValue();
        UserEntity userEntity = testUserEntity(userValue);
        given(userRepository.existsByEmailAndUserStatus(userEntity.getEmail(), UserStatus.ACTIVE)).willReturn(Boolean.TRUE);

        assertThatThrownBy(() -> userJoinService.userSave(userValue))
                .isInstanceOf(UserException.class)
                .hasMessage(UserExceptionMessage.DUPLICATED_EMAIL.getErrorMessage());

        verify(userRepository, times(1)).existsByEmailAndUserStatus(userEntity.getEmail(), UserStatus.ACTIVE);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    @DisplayName("유저 회원가입 시 history 저장 테스트 - 성공")
    void canUserStatusHistorySave() {
        UserStatusHistory userStatusHistory = testUserSaveHistory(1L);

        given(userStatusHistoryRepository.save(any(UserStatusHistory.class))).willReturn(userStatusHistory);
        userJoinService.userStatusHistorySave(userStatusHistory.getUserId(), userStatusHistory.getUserStatus());

        verify(userStatusHistoryRepository, times(1)).save(any(UserStatusHistory.class));
        verifyNoMoreInteractions(userStatusHistoryRepository);
    }

    @Test
    @DisplayName("유저 회원가입 시 history 저장 테스트 - 실패")
    void cannotUserStatusHistorySave() {
        UserStatusHistory userStatusHistory = testUserSaveHistory(null);

        assertThatThrownBy(
                () -> userJoinService.userStatusHistorySave(userStatusHistory.getUserId(), userStatusHistory.getUserStatus()))
                .isInstanceOf(UserException.class)
                .hasMessage(UserExceptionMessage.HISTORY_SAVE_FAIL.getErrorMessage());

        // 문제가 있을 때, save 되면 안되서 확인
        verify(userStatusHistoryRepository, times(0)).save(any(UserStatusHistory.class));
        verifyNoMoreInteractions(userStatusHistoryRepository);
    }

    @Test
    @DisplayName("유저 로그인 성공")
    void canUserLogin(){
        UserValue userValue = createUserValue();
        UserEntity userEntity = testUserEntity(userValue);
        given(userRepository.findByEmail(userValue.getEmail())).willReturn(Optional.ofNullable(userEntity));
        given(encryption.encrypt(userValue.getPassword(), Objects.requireNonNull(userEntity).getSalt())).willReturn(password);

        userJoinService.loginUser(userValue);

        verify(userRepository, times(1)).findByEmail(userValue.getEmail());
        verify(loginAuthRepository, times(1)).save(any());
        verifyNoMoreInteractions(userRepository);
        verifyNoMoreInteractions(loginAuthRepository);
    }

    @Test
    @DisplayName("유저 로그인 실패 - 해당 유저가 없음")
    void cannotUserLoginNotExistUser() {
        UserValue userValue = createUserValue();
        given(userRepository.findByEmail(userValue.getEmail())).willReturn(Optional.empty());

        assertThatThrownBy(() -> userJoinService.loginUser(userValue))
                .isInstanceOf(UserException.class)
                .hasMessage(UserExceptionMessage.USER_NOT_FOUNT.getErrorMessage());

        verify(userRepository, times(1)).findByEmail(userValue.getEmail());
        verify(loginAuthRepository, times(0)).save(any());
        verifyNoMoreInteractions(userRepository);
        verifyNoMoreInteractions(loginAuthRepository);
    }

    @Test
    @DisplayName("유저 로그인 실패 - 비밀번호가 다름")
    void cannotUserLoginNotCollectPassword(){
        UserValue userValue = createUserValue();
        UserEntity userEntity = testUserEntity(userValue);
        given(userRepository.findByEmail(userValue.getEmail())).willReturn(Optional.ofNullable(userEntity));
        given(encryption.encrypt(userValue.getPassword(), Objects.requireNonNull(userEntity).getSalt())).willReturn(password + "1");

        assertThatThrownBy(() -> userJoinService.loginUser(userValue))
                .isInstanceOf(UserException.class)
                .hasMessage(UserExceptionMessage.WRONG_PASSWORD.getErrorMessage());

        verify(userRepository, times(1)).findByEmail(userValue.getEmail());
        verify(loginAuthRepository, times(0)).save(any());
        verifyNoMoreInteractions(userRepository);
        verifyNoMoreInteractions(loginAuthRepository);
    }

    //유닛 테스트 - 자바 로직이 잘 돌아가는지 확인하는 테스트 - 누가 리펙토링해도 안정적이게 + 빠른 피드백
    // 인테그레이션 테스트 - 전체 테스트 개념 - 전체 테스트 관련도 작성 필요

}