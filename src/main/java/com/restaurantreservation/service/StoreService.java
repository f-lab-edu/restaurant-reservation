package com.restaurantreservation.service;

import com.restaurantreservation.domain.Review;
import com.restaurantreservation.domain.Store;
import com.restaurantreservation.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;

    /**
     * 지역 1depth로 음식점 목록을 조회
     * @param region_depth1
     * @param offset
     * @param limit
     * @return
     */
    public List<Store> findByRegionDepth1(Long region_depth1, int offset, int limit) {
        return storeRepository.findByRegionDepth1(region_depth1, offset, limit);
    }

    /**
     * 음식점의 리뷰 목록을 조회
     * @param store_id
     * @param offset
     * @param limit
     * @return
     */
    public List<Review> findStoreReview(Long store_id, int offset, int limit) {
        return storeRepository.findStoreReviews(store_id, offset, limit);
    }
}
