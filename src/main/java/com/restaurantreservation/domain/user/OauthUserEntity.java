package com.restaurantreservation.domain.user;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 구글 로그인 등 소셜로그인 부분 구현 예정
 */
@Entity
@Table(name = "oauth_user")
public class OauthUserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updateDate;

    private String snsKind;
    private String snsKey;
    //추가 필요한게 머가 있을까 생각 해 보기

    //user하고 연결하기 위해 사용 예정
    private Long userId;

}
