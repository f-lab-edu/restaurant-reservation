package com.restaurantreservation.dto;

import com.restaurantreservation.domain.Review;
import com.restaurantreservation.domain.Store;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Value
public class StoreDto {

    @Getter
    String name;               // 가게 이름
    @Getter
    String address;            // 가게 주소
    @Getter
    Long categoryId;           // 카테고리
    @Getter
    String telephone;          // 카테고리
    @Getter
    String businessHour;       // 영업 시간
    @Getter
    List<MenuDto> menus;       // 가게 메뉴
    @Getter
    List<ReviewDto> reviews;   // 가게 리뷰

    public StoreDto(Store s, List<Review> reviewList) {
        name         = s.getName();
        address      = s.getAddress();
        categoryId   = s.getCategoryId();
        businessHour = s.getBusinessHour();
        menus        = s.getMenus().stream().map(m -> new MenuDto(m)).collect(Collectors.toList());
        reviews      = reviewList.stream().map(r -> new ReviewDto(r)).collect(Collectors.toList());
        telephone    = s.getTelephone();
    }
}
