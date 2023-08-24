package com.hmcuong.fooddelivery.DTO.DonDatHang;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderRequestDTO {

    private String diaChiGiaoHang;

    private List<CartRequestDTO> cartRequestDTOS = new ArrayList<>();

    public boolean hasNullDataBeforeInsert() {
        return cartRequestDTOS == null || diaChiGiaoHang == null;
    }
}
