package com.restaurantreservation.domain.history;

import com.restaurantreservation.domain.user.UserStatus;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 회원 상태 변회 기록 테이블 (가입, 탈퇴 등)
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_status_history")
public class UserStatusHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    private Long userId;
    private UserStatus userStatus;

    public static UserStatusHistory of(Long userId, UserStatus userStatus) {
        return new UserStatusHistory(userId, userStatus);
    }

    private UserStatusHistory(Long userId, UserStatus userStatus) {
        this.userId = userId;
        this.userStatus = userStatus;
    }
}
