package com.hmcuong.fooddelivery.DTO.MonAn;

import lombok.Data;

@Data
public class MonAnDTO {
    private String idMonAn;
    private String TenMon;
    private String MieuTaMon;
    private double DonGia;
    private String TinhTrangMon;
    private String TuyChon;

    public boolean hasNullDataBeforeRegister(MonAnDTO monAnDTO) {
        return
            monAnDTO.getIdMonAn() == null ||
            monAnDTO.getTenMon() == null ||
            monAnDTO.getMieuTaMon() == null ||
            monAnDTO.getTinhTrangMon() == null
        ;
    }
}
