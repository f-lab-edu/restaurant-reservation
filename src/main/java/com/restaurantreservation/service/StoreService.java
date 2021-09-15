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
import org.springframework.util.StringUtils;

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
     * 음식점 목록을 조회
     * @param regionDepth1  지역 1depth
     * @param name          가게 이름
     * @param offset        어디서부터
     * @param limit         어디까지 조회
     * @return              음식점 목록
     */
    public List<StoreDto> getStoreList(Long regionDepth1, String name, int offset, int limit) {

        //final List<StoreDto> stores;
        final PageRequest pageRequest = PageRequest.of(offset, limit);
        final Page<Store> page;

        if (regionDepth1 != 0 && StringUtils.hasText(name)) {
            page = storeRepository.findStoreByRegionDepth1(regionDepth1, pageRequest);
        } else {
            page = storeRepository.findByRegionDepth1AndNameContaining(regionDepth1, name, pageRequest);
        }

        return page.getContent()
                .stream()
                .map(s -> StoreDto.convertStore(s, this.findStoreReview(s.getId())))
                .collect(toList());
    }

    /**
     * 음식점의 리뷰 목록을 조회
     * @param id        음식점 id
     * @return          음식점의 리뷰 목록
     */
    public List<Review> findStoreReview(Long id) {
        return reviewRepository.findReviewByStoreId(id);
    }
}
