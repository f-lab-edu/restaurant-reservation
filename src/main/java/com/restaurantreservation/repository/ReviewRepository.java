package com.restaurantreservation.repository;

import com.restaurantreservation.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findReviewByStoreId(Long storeId);
}
