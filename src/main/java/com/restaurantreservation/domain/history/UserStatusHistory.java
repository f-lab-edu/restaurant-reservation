package com.restaurantreservation.domain.history;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 회원 상태 변회 기록 테이블 (가입, 탈퇴 등)
 */
@Entity
@Table(name = "user_status_history")
public class UserStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    private Long userId;
    private String userStatus;
}
