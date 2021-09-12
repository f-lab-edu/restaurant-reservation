package com.restaurantreservation.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Review {

    @Id
    private Long id;

    private String content;
    private Long userId;
    private int score;
    private Long storeId;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updateDate;

}
