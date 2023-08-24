package com.hmcuong.fooddelivery.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "temporary_cart")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemporaryCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "food_item_id")
    private String foodItemId;
}
