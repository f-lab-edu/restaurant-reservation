package com.restaurantreservation.domain.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * user - DB 연결하는 부분
 * 주의점
 * 1. 비밀번호는 암호화 된 채로 들어가야 합니다.
 * 2. 비밀번호가 client 단까지 노출되지 않게 신경을 써야 합니다.
 */
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@ToString
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
public class UserEntity {

    /**
     * email - 회원 ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(name = "created_date", updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "update_date")
    @LastModifiedDate
    private LocalDateTime updateDate;

    private String email;

    private String password;

    //패스워드 소팅 글자를 같이 저장 - 실무에서는 다른 저장 방법같은 경우로 고려
    private String salt;

    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    // 회원 유형 - 업체, 손님 등등
    @Column(name = "user_type")
    @Enumerated(value = EnumType.STRING)
    private UserType userType;

    // 유저 상태 체크 - 회원가입 대기 , 활동, 휴먼, 탈퇴 등등
    @Column(name = "user_status")
    @Enumerated(value = EnumType.STRING)
    private UserStatus userStatus;

    public static UserEntity create(String email, String encryptedPassword, String salt, String name, String phoneNumber, UserType userType) {
        return new UserEntity(
                email,
                encryptedPassword,
                salt,
                name,
                phoneNumber,
                userType,
                UserStatus.ACTIVE
        );
    }

    public static UserEntity createTest(Long id,String email, String encryptedPassword, String salt, String name, String phoneNumber, UserType userType) {
        return new UserEntity(
                1L,
                email,
                encryptedPassword,
                salt,
                name,
                phoneNumber,
                userType,
                UserStatus.ACTIVE
        );
    }

    private UserEntity(String email, String password,String salt, String name, String phoneNumber, UserType userType, UserStatus userStatus) {
        this.email = email;
        this.password = password;
        this.salt = salt;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.userType = userType;
        this.userStatus = userStatus;
    }

    public UserEntity(Long id, String email, String password, String salt, String name, String phoneNumber, UserType userType, UserStatus userStatus) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.salt = salt;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.userType = userType;
        this.userStatus = userStatus;
    }
}
