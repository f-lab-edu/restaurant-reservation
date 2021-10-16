package com.restaurantreservation.controller;

import com.restaurantreservation.dto.StoreDto;
import com.restaurantreservation.service.StoreService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class StoreApiController {

    private final StoreService storeService;

    /**
     * 지역 1depth로 음식점 목록을 조회
     * @param regionDepth1     지역 1depth
     * @param offset            어디서부터
     * @param limit             어디까지 조회
     * @return                  음식점 목록
     */
    @GetMapping("/api/search/stores")
    public Result<List<StoreDto>> searchStore(@RequestParam (value = "regionDepth1", defaultValue = "1000") Long regionDepth1,
                                              @RequestParam(value = "name") String name,
                                              @RequestParam(value = "offset", defaultValue = "0") int offset,
                                              @RequestParam(value = "limit", defaultValue = "100") int limit) {

        final List<StoreDto> stores = storeService.getStoreList(regionDepth1, name, offset, limit);
        return new Result<>(stores.size(), stores);
    }

    @Value
    @AllArgsConstructor
    static class Result<T> {
        int count;
        T data;
    }
}
