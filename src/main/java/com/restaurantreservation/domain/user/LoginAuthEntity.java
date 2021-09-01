package com.restaurantreservation.domain.user;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "login_auth")
public class LoginAuthEntity {

    @Id
    private Long id;

    private String authTokenKey;
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;
    //만료시간
    private LocalDateTime expireDate;


}
