package com.restaurantreservation.dto;

import com.restaurantreservation.domain.Review;
import lombok.Getter;
import lombok.Value;

@Value
public class ReviewDto {

    @Getter
    String content;    // 리뷰 내용
    @Getter
    long score;        // 리뷰 점수

    public ReviewDto(Review review) {
        content = review.getContent();
        score   = review.getScore();
    }
}
