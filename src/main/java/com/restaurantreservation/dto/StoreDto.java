package com.restaurantreservation.dto;

import com.restaurantreservation.domain.Review;
import com.restaurantreservation.domain.Store;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Value
public class StoreDto {

    String name;               // 가게 이름
    String address;            // 가게 주소
    Long categoryId;           // 카테고리
    String telephone;          // 카테고리
    String businessHour;       // 영업 시간
    List<MenuDto> menus;       // 가게 메뉴
    List<ReviewDto> reviews;   // 가게 리뷰

    private StoreDto(Store s, List<Review> reviewList) {
        name         = s.getName();
        address      = s.getAddress();
        categoryId   = s.getCategoryId();
        businessHour = s.getBusinessHour();
        menus        = s.getMenus().stream().map(m -> MenuDto.convertMenu(m)).collect(Collectors.toList());
        reviews      = reviewList.stream().map(r -> ReviewDto.convertReview(r)).collect(Collectors.toList());
        telephone    = s.getTelephone();
    }

    public static StoreDto convertStore(Store store, List<Review> reviewList) {
        return new StoreDto(store, reviewList);
    }
}
