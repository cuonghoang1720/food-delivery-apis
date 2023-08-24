package com.hmcuong.fooddelivery.DTO.DonDatHang;

import lombok.Data;

import java.io.Serializable;

@Data
public class CartRequestDTO implements Serializable {
    private String idMonAn;
    private String soLuong;
    private String tuyChon;

    public boolean hasNullDataBeforeInsert(){
        return idMonAn == null || soLuong == null;
    }
}
