package com.restaurantreservation.repository.user;

import com.restaurantreservation.domain.user.LoginAuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginAuthRepository extends JpaRepository<LoginAuthEntity, Long> {
    Optional<LoginAuthEntity> findByAuthTokenKey(String tokenKey);

    Optional<LoginAuthEntity> findByUserIdAndAuthTokenKey(long userId, String tokenKey);

    Boolean existsByUserIdAndAuthTokenKey(long userId, String tokenKey);
}
