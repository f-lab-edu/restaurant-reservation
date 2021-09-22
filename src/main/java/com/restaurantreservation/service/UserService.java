package com.restaurantreservation.service;

import com.restaurantreservation.domain.history.UserStatusHistory;
import com.restaurantreservation.domain.user.LoginAuthEntity;
import com.restaurantreservation.domain.user.UserEntity;
import com.restaurantreservation.domain.user.UserStatus;
import com.restaurantreservation.domain.user.UserValue;
import com.restaurantreservation.encrypt.Encryption;
import com.restaurantreservation.error.exception.user.UserException;
import com.restaurantreservation.error.message.user.UserExceptionMessage;
import com.restaurantreservation.repository.history.UserStatusHistoryRepository;
import com.restaurantreservation.repository.user.LoginAuthRepository;
import com.restaurantreservation.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import java.util.UUID;


@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final Encryption encryption;
    private final UserStatusHistoryRepository userStatusHistoryRepository;

    private final LoginAuthRepository loginAuthRepository;

    private static final Long LOGIN_SESSION_TIME = 5 * 60L;
    private static final String LOGIN_SESSION_NAME = "login_session";

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
     * 멀티 스레드 환경일때 어떻게 DB 를 관리 할 지 생각하면서 구현 필요
     */
    // 메서드 앞을 동사로
    public void userLogin(UserValue userValue) {
        UserEntity userEntity = emailCheck(userValue);
        passwordCheck(userValue, userEntity.getPassword(), userEntity.getSalt());
        //로그인 세션 생성 및 저장
        loginSessionCreateAndSave(userEntity.getId());
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

    /**
     * 멀티 프로세스 환경에서는 내부의 session 으로는 동기화가 어렵기때문에
     * 현재는 DB 에 저장해서 확인 - (나중에는 Redis DB 를 사용해 옮길 예정)
     */
    private void loginSessionCreateAndSave(Long userId) {
        String loginTokenKey = UUID.randomUUID().toString();
        loginAuthRepository.save(
                LoginAuthEntity.create(userId, loginTokenKey, LOGIN_SESSION_TIME)
        );
        new Cookie(LOGIN_SESSION_NAME, loginTokenKey);

        //토큰을 직접 내려주고 expire 시간으로 관리하는 방향으로 찾아서 바꾸어 보자.

        //access 토큰  - 모든 인증은 여기서
        //refresh 토큰 - access 토큰을 발급받기 위한 토큰 >> 찾아서 바꿔볼 것
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
