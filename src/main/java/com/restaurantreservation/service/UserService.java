package com.restaurantreservation.service;

import com.restaurantreservation.domain.history.UserStatusHistory;
import com.restaurantreservation.domain.user.LoginAuthEntity;
import com.restaurantreservation.domain.user.UserEntity;
import com.restaurantreservation.domain.user.UserStatus;
import com.restaurantreservation.domain.user.UserValue;
import com.restaurantreservation.domain.user.login.JwtTokenDto;
import com.restaurantreservation.domain.user.login.JwtTokenProvider;
import com.restaurantreservation.domain.user.login.JwtType;
import com.restaurantreservation.domain.user.login.TokenValue;
import com.restaurantreservation.encrypt.Encryption;
import com.restaurantreservation.error.exception.user.UserException;
import com.restaurantreservation.error.message.user.JWTTokenExceptionMessage;
import com.restaurantreservation.error.message.user.UserExceptionMessage;
import com.restaurantreservation.repository.history.UserStatusHistoryRepository;
import com.restaurantreservation.repository.user.LoginAuthRepository;
import com.restaurantreservation.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final Encryption encryption;
    private final UserStatusHistoryRepository userStatusHistoryRepository;

    private final LoginAuthRepository loginAuthRepository;

    private static final Long TOKEN_EXPIRED_TERM = 5 * 60L;

    /**
     * 회원 저장 로직
     */
    public Long userSave(UserValue userValue) {
        //이미 저장된 아이디(email) 인지 체크
        emailDuplicateCheck(userValue);
        //신규 salt 생성
        String salt = encryption.createSALT();
        //패스워드 암호화
        String encryptedPassword = encryption.encrypt(userValue.getPassword(), salt);
        //userEntity 생성 - 암호화 처리까지된것
        UserEntity userEntity = createUserEntity(userValue, salt, encryptedPassword);

        UserEntity entity = userRepository.save(userEntity);

        return entity.getId();
    }

    /**
     * history 에 저장
     */
    public void userStatusHistorySave(Long userId, UserStatus userStatus) {
        if (userId == null || userStatus == null) {
            throw new UserException(UserExceptionMessage.HISTORY_SAVE_FAIL);
        }
        userStatusHistoryRepository.save(UserStatusHistory.of(userId, userStatus));
    }

    /**
     * user 를 저장하고 그 기록을 history 에 저장
     */
    @Transactional
    public void userSaveAndUserHistorySave(UserValue userValue) {
        Long userId = userSave(userValue);
        userStatusHistorySave(userId, UserStatus.ACTIVE);
    }

    /**
     * userlogin 처리 로직
     * 멀티 스레드 환경일때 어떻게 로그인 상태를 관리 할 지 생각하면서 구현 필요
     * <p>
     * 로그인 성공하면 AccessToken, RefreshToken 생성 후 넘겨줌
     */
    //map 은 최대한 줄이고 객체를 만들어서 넘겨보자.
    public JwtTokenDto loginUser(UserValue userValue) {
        UserEntity userEntity = emailCheck(userValue);
        passwordCheck(userValue, userEntity.getPassword(), userEntity.getSalt());
        //확인, 통과

        // Access token 및 refresh token 생성
        String accessToken = createJwtToken(JwtType.ACCESS_TOKEN, userEntity.getId(), userEntity.getEmail());
        String refreshToken = createJwtToken(JwtType.REFRESH_TOKEN, userEntity.getId(), userEntity.getEmail());

        //  refresh token 은 DB 에 저장
        LoginAuthEntity loginAuthEntity = LoginAuthEntity.create(userEntity.getId(), refreshToken, TOKEN_EXPIRED_TERM);
        loginAuthRepository.save(loginAuthEntity);

        return JwtTokenDto.create(accessToken, refreshToken);
    }

    private String createJwtToken(JwtType jwtType, Long userId, String email) {
        return JwtTokenProvider.createJwtToken(userId, email, jwtType);
    }

    //accessToken 재발급 -> RefreshToken 확인 후 생성

    /**
     * access token 재발급 로직
     * refreshToken 이 DB 에 저장된 것들과 일치한 지 확인 후
     * Access Token 재발급
     */
    public JwtTokenDto reissueAccessToken(String refreshToken) {
        //유효한 토큰인지 검사
        JwtTokenProvider.isValidToken(refreshToken, JwtType.REFRESH_TOKEN);

        TokenValue userIdAndEmail = JwtTokenProvider.getUserIdAndEmail(refreshToken, JwtType.REFRESH_TOKEN);

        long userId = userIdAndEmail.getUserId();
        String email = userIdAndEmail.getMemberId();

        if (!loginAuthRepository.existsByUserIdAndAuthTokenKey(userId, refreshToken)) {
            throw new UserException(JWTTokenExceptionMessage.REFRESH_TOKEN_NOT_EXISTS);
        }
        //Access token 생성
        String newAccessToken = createJwtToken(JwtType.ACCESS_TOKEN, userId, email);

        return JwtTokenDto.createAccessToken(newAccessToken);
    }

    public void checkAccessToken(String accessToken) {
        JwtTokenProvider.isValidToken(accessToken, JwtType.ACCESS_TOKEN);
    }

    private UserEntity emailCheck(UserValue userValue) {
        return userRepository.findByEmail(userValue.getEmail()).orElseThrow(
                () -> new UserException(UserExceptionMessage.USER_NOT_FOUNT)
        );
    }

    private void passwordCheck(UserValue userValue, String userEntityPassword, String salt) {
        String inputPassword = encryption.encrypt(userValue.getPassword(), salt);
        if (!inputPassword.equals(userEntityPassword)) {
            throw new UserException(UserExceptionMessage.WRONG_PASSWORD);
        }
    }

    private UserEntity createUserEntity(UserValue userValue, String salt, String encryptedPassword) {
        return UserEntity.create(
                userValue.getEmail(),
                encryptedPassword,
                salt,
                userValue.getName(),
                userValue.getPhoneNumber(),
                userValue.getUserType());
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
