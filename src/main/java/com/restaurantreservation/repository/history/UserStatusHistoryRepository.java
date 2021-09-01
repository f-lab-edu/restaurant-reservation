package com.restaurantreservation.repository.history;

import com.restaurantreservation.domain.history.UserStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStatusHistoryRepository extends JpaRepository<UserStatusHistory, Long> {
}
