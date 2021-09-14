package com.restaurantreservation.repository.user;

import com.restaurantreservation.domain.user.UserEntity;
import com.restaurantreservation.domain.user.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Boolean existsByEmailAndUserStatus(String email, UserStatus userStatus);

    Optional<UserEntity> findByEmail(String email);
}
