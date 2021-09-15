package com.restaurantreservation.repository.user;

import com.restaurantreservation.domain.user.EmailAuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailAuthRepository extends JpaRepository<EmailAuthEntity, Long> {
}
