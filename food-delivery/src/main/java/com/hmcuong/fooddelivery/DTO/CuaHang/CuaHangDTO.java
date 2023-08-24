package com.hmcuong.fooddelivery.DTO.CuaHang;

import lombok.Data;

@Data
public class CuaHangDTO {
    private String idCuaHang;
    private String tenCuaHang;
    private String hoatDong;
    private String tinhTrang;
    private String diaChi;
    private String idThucDon;

    public boolean hasNullDataBeforeRegister(CuaHangDTO cuaHangDTO) {
        return
            cuaHangDTO.getIdCuaHang() == null
            || cuaHangDTO.getHoatDong() == null
            || cuaHangDTO.getTenCuaHang() == null
            || cuaHangDTO.getDiaChi() == null
            || cuaHangDTO.getTinhTrang() == null
            || cuaHangDTO.getIdThucDon() == null
        ;
    }
}
