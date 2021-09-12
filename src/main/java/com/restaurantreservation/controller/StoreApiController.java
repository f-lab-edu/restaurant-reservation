package com.restaurantreservation.controller;

import com.restaurantreservation.dto.StoreDto;
import com.restaurantreservation.service.StoreService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class StoreApiController {

    private final StoreService storeService;

    /**
     * 지역 1depth로 음식점 목록을 조회
     * @param region_depth1     지역 1depth
     * @param offset            어디서부터
     * @param limit             어디까지 조회
     * @return                  음식점 목록
     */
    @GetMapping("/api/search/stores")
    public Result<List<StoreDto>> searchStore(@RequestParam Map<String, Object> paramsMap,
                                              @RequestParam(value = "offset", defaultValue = "0") int offset,
                                              @RequestParam(value = "limit", defaultValue = "100") int limit) {

        Long regionDepth1 = paramsMap.get("region_depth1") == null ? 0 : Long.valueOf((String) paramsMap.get("region_depth1"));
        String name = paramsMap.get("name") == null ? "" : (String) paramsMap.get("name");
        List<StoreDto> stores = new ArrayList<>();

        if (regionDepth1 != 0 && StringUtils.hasText(name)) {
            stores = storeService.findByRegionDepth1AndName(regionDepth1, name, offset, limit);
        } else {
            stores = storeService.findByRegionDepth1(regionDepth1, offset, limit);
        }

        return new Result<>(stores.size(), stores);
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private int count;
        private T data;
    }
}
