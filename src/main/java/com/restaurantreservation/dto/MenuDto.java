package com.restaurantreservation.dto;

import com.restaurantreservation.domain.Menu;
import lombok.Value;

@Value
public class MenuDto {

    String menuName;    // 메뉴명
    long menuPrice;     // 가격

    private MenuDto(Menu menu) {
        menuName  = menu.getName();
        menuPrice = menu.getPrice();
    }

    public static MenuDto convertMenu(Menu menu) {
        return new MenuDto(menu);
    }
}
