package com.restaurantreservation.domain.user;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;


@Getter
@Entity
@Table(name = "login_auth")
public class LoginAuthEntity {

    @Id
    private Long id;

    private Long userId;

    private String authTokenKey;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;
    //만료시간
    private LocalDateTime expireDate;

    public static LoginAuthEntity create(Long userId, String loginTokenKey, Long sessionTime) {
        LocalDateTime expireDate = LocalDateTime.now().plusMinutes(sessionTime);
        return new LoginAuthEntity(null, userId, loginTokenKey, null, expireDate);
    }

    public static LoginAuthEntity updateExpireDate(LoginAuthEntity loginAuthEntity, Long sessionTime) {
        LocalDateTime expireDate = LocalDateTime.now().plusMinutes(sessionTime);
        return new LoginAuthEntity(loginAuthEntity.getId(), loginAuthEntity.getUserId(), loginAuthEntity.getAuthTokenKey(), null, expireDate);
    }

    protected LoginAuthEntity() {
    }

    private LoginAuthEntity(Long id, Long userId, String authTokenKey, LocalDateTime createdDate, LocalDateTime expireDate) {
        this.id = id;
        this.userId = userId;
        this.authTokenKey = authTokenKey;
        this.createdDate = createdDate;
        this.expireDate = expireDate;
    }



}
