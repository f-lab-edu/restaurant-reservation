package com.restaurantreservation.dto;

import com.restaurantreservation.domain.Review;
import lombok.Value;

@Value
public class ReviewDto {

    String content;    // 리뷰 내용
    long score;        // 리뷰 점수

    private ReviewDto(Review review) {
        content = review.getContent();
        score   = review.getScore();
    }

    public static ReviewDto convertReview(Review review) {
        return new ReviewDto(review);
    }
}
