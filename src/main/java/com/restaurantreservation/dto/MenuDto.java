package com.restaurantreservation.dto;

import com.restaurantreservation.domain.Menu;
import lombok.Getter;
import lombok.Value;

@Value
public class MenuDto {

    @Getter
    String menuName;    // 메뉴명
    @Getter
    long menuPrice;     // 가격

    public MenuDto(Menu menu) {
        menuName  = menu.getName();
        menuPrice = menu.getPrice();
    }
}
