package com.restaurantreservation.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Store {

    @Id
    private Long id;

    private String name;
    private String address;
    private Long category_id;
    private Long region_depth1;
    private Long region_depth2;
    private String business_hour;
    private String telephone;

    @OneToMany(mappedBy = "store")
    private List<Menu> menus = new ArrayList<>();

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updateDate;

}
