package com.restaurantreservation.controller;

import com.restaurantreservation.domain.Menu;
import com.restaurantreservation.domain.Review;
import com.restaurantreservation.domain.Store;
import com.restaurantreservation.service.StoreService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class StoreApiController {

    private final StoreService storeService;

    /**
     * 지역 1depth로 음식점 목록을 조회
     * @param region_depth1
     * @param offset
     * @param limit
     * @return
     */
    @GetMapping("/api/search/stores")
    public Result searchStore(@RequestParam(value = "region_depth1", defaultValue = "1000") Long region_depth1,
                              @RequestParam(value = "offset", defaultValue = "0") int offset,
                              @RequestParam(value = "limit", defaultValue = "100") int limit) {

        List<Store> stores = storeService.findByRegionDepth1(region_depth1, offset, limit);

        List<StoreDto> result = stores.stream()
                .map(s -> new StoreDto(s))
                .collect(toList());

        // 가게 리뷰 단건씩 조회 후 StoreDto에 set
        result.forEach(o -> {
            List<ReviewDto> reviews = storeService.findStoreReview(o.getStore_id(), 0, 5).stream()
                    .map(s -> new ReviewDto(s))
                    .collect(toList());
            o.setReviews(reviews);
        });

        return new Result(result);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }

    @Data
    static class StoreDto {

        private Long store_id;              // 가게 id
        private String name;                // 가게 이름
        private String address;             // 가게 주소
        private Long category_id;           // 카테고리
        private String business_hour;       // 영업 시간
        private List<MenuDto> menus;        // 가게 메뉴
        private List<ReviewDto> reviews;    // 가게 리뷰

        public StoreDto(Store store) {
            store_id = store.getId();
            name = store.getName();
            address = store.getAddress();
            category_id = store.getCategory_id();
            business_hour = store.getBusiness_hour();
            menus = store.getMenus().stream()
                    .map(menu -> new MenuDto(menu))
                    .collect(toList());
        }
    }

    @Data
    static class MenuDto {

        private String menuName;    // 메뉴명
        private long menuPrice;     // 가격

        public MenuDto(Menu menu) {
            menuName = menu.getName();
            menuPrice = menu.getPrice();
        }
    }

    @Data
    static class ReviewDto {

        private String content;    // 리뷰 내용
        private long score;        // 리뷰 점수

        public ReviewDto(Review review) {
            content = review.getContent();
            score = review.getScore();
        }
    }
}
