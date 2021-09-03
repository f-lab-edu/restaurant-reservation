package com.restaurantreservation.domain.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@ToString
@Table(name = "user")
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

    public static UserEntity create(UserValue userValue) {
        return new UserEntity(userValue.getEmail(), userValue.getPassword(), userValue.getName(),
                userValue.getPhoneNumber(), userValue.getUserType(), userValue.getUserStatus());
    }

    private UserEntity(String email, String password, String name, String phoneNumber, UserType userType, UserStatus userStatus) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.userType = userType;
        this.userStatus = userStatus;
    }
}
