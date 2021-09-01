package com.restaurantreservation.domain.user;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "user")
public class UserEntity{
    //상속은 한번만 할 수 있기떄문에 최대한 줄이자

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @org.springframework.data.annotation.LastModifiedDate
    private LocalDateTime updateDate;

    @Column(length = 255)
    private String password;

    private String name;

    private String phoneNumber;

    private String address;

    private String userType;

    // 유저 상태 체크 - 회원가입 대기 , 활동, 휴먼, 탈퇴 등등
    @Column(name = "user_status", length = 50)
    private String userStatus;

}
