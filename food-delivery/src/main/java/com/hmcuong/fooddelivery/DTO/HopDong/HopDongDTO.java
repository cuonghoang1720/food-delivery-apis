package com.hmcuong.fooddelivery.DTO.HopDong;

import com.hmcuong.fooddelivery.entity.HopDong;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class HopDongDTO {
    private String id_HopDong;
    private Timestamp ngayLap;
    private Timestamp ngayKetThuc;
    private String maSoThue;
    private String nguoiDaiDien;
    private int soChiNhanhDangKy;
    private String diaChiCacChiNhanh;
    private String soTaiKhoan;
    private String nganHang;
    private String chiNhanhNganHang;
    private double phiKichHoat;
    private int lanGiaHan;
    private String MoTa;

    public boolean hasNullDataBeforeRegister(HopDongDTO hopDongDTO){
        return
                hopDongDTO.getId_HopDong() == null
                        || hopDongDTO.getChiNhanhNganHang() == null
                        || hopDongDTO.getMoTa() == null
                        || hopDongDTO.getNgayLap() == null
                        || hopDongDTO.getNguoiDaiDien() == null
                        || hopDongDTO.getNgayKetThuc() == null
                        || hopDongDTO.getMaSoThue() == null
                        || hopDongDTO.getSoTaiKhoan() == null
                ;
    }
}
