package com.restaurantreservation.repository;

import com.restaurantreservation.domain.Review;
import com.restaurantreservation.domain.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class StoreRepository {

    private final EntityManager em;

    /**
     * 지역 1depth로 음식점 목록을 조회
     * @param region_depth1     지역 1depth key
     * @param offset            
     * @param limit
     * @return                  지역 1depth에 해당하는 음식점 목록
     */
    public List<Store> findByRegionDepth1(Long region_depth1, int offset, int limit) {
        return em.createQuery("select s from Store s " +
                        " left outer join fetch s.menus m" +
                        " where s.region_depth1 = :region_depth1", Store.class)
                .setParameter("region_depth1", region_depth1)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    /**
     * 음식점의 리뷰 목록을 조회
     * @param store_id  음식점 id
     * @param offset
     * @param limit     
     * @return          리뷰 리스트
     */
    public List<Review> findStoreReviews(Long store_id, int offset, int limit) {
        return em.createQuery("select s from Review s " +
                        " where s.store_id = :store_id", Review.class)
                .setParameter("store_id", store_id)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }
}
