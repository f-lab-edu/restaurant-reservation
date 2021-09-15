package com.restaurantreservation.domain.user;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * join_auth - 회원가입할때 이메일 인증 테이블
 */
@Entity
@Table(name = "email_auth")
public class EmailAuthEntity {

    @Id
    private Long id;

    //인증 키 생성 시간
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updateDate;

    private Long userId;
    //회원가입 시 인증 키
    private String authKey;

}
