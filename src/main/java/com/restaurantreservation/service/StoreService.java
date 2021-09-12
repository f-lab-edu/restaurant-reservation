package com.restaurantreservation.service;

import com.restaurantreservation.domain.Review;
import com.restaurantreservation.domain.Store;
import com.restaurantreservation.dto.StoreDto;
import com.restaurantreservation.repository.ReviewRepository;
import com.restaurantreservation.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class StoreService {

    //private final StoreJpaRepository storeJpaRepository;
    private final StoreRepository storeRepository;
    private final ReviewRepository reviewRepository;

    /**
     * 지역 1depth로 음식점 목록을 조회
     * @param region_depth1 지역 1depth
     * @param offset        어디서부터
     * @param limit         어디까지 조회
     * @return              음식점 목록
     */
    public List<StoreDto> findByRegionDepth1(Long region_depth1, int offset, int limit) {
        PageRequest pageRequest = PageRequest.of(offset, limit);
        Page<Store> page = storeRepository.findStoreByRegionDepth1(region_depth1, pageRequest);

        return page.getContent()
                .stream()
                .map(s -> new StoreDto(s, this.findStoreReview(s.getId(), 0, 5)))
                .collect(toList());
    }

    /**
     * 음식점의 리뷰 목록을 조회
     * @param id        음식점 id
     * @param offset    어디서부터
     * @param limit     어디까지 조회
     * @return          음식점의 리뷰 목록
     */
    public List<Review> findStoreReview(Long id, int offset, int limit) {
        return reviewRepository.findReviewByStoreId(id);
    }

    public List<StoreDto> findByRegionDepth1AndName(Long regionDepth1, String name, int offset, int limit) {
        PageRequest pageRequest = PageRequest.of(offset, limit);
        Page<Store> page = storeRepository.findByRegionDepth1AndNameContaining(regionDepth1, name, pageRequest);

        return page.getContent()
                .stream()
                .map(s -> new StoreDto(s, this.findStoreReview(s.getId(), 0, 5)))
                .collect(toList());
    }
}
