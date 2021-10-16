package com.restaurantreservation.repository;

import com.restaurantreservation.domain.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StoreRepository extends JpaRepository<Store, Long> {

    //List<Store> findStoreByRegionDepth1(Long regionDepth1);
    Page<Store> findStoreByRegionDepth1(Long regionDepth1, Pageable pageable);

    @Query("select s from Store s where s.regionDepth1 = ?1 and s.name like %?2%")
    Page<Store> findByRegionDepth1AndNameContaining(Long regionDepth1, String name, Pageable pageable);
}
