package com.restaurantreservation.repository.user;

import com.restaurantreservation.domain.user.LoginAuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginAuthRepository extends JpaRepository<LoginAuthEntity, Long> {
}
