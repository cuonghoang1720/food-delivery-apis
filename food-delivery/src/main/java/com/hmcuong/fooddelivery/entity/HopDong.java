package com.hmcuong.fooddelivery.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "hopdong")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HopDong {

    @Id
    @Column(name = "id_hopdong")
    private String id_HopDong;

    @Column(name = "ngaylap")
    private Timestamp ngayLap;

    @Column(name = "ngayketthuc")
    private Timestamp ngayKetThuc;

    @Column(name = "masothue",unique = true)
    private String maSoThue;

    @Column(name = "nguoidaidien")
    private String nguoiDaiDien;

    @Column(name = "sochinhanhdangky")
    private int soChiNhanhDangKy;

    @Column(name = "diachicacchinhanh")
    private String diaChiCacChiNhanh;

    @Column(name = "sotaikhoan")
    private String soTaiKhoan;

    @Column(name = "nganhang")
    private String nganHang;

    @Column(name = "chinhanhnganhang")
    private String chiNhanhNganHang;

    @Column(name = "phikichhoat")
    private double phiKichHoat;

    @Column(name = "langiahan")
    private int lanGiaHan;

    @Column(name = "mota")
    private String MoTa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "id_doitac",
            nullable = false,
            referencedColumnName = "id_doitac",
            foreignKey = @ForeignKey(name = "HopDong_DoiTac_Fk")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private DoiTac doiTac;

    public boolean hasNullDataBeforeRegister(HopDong hopDong){
        return
                hopDong.getId_HopDong() == null
                        || hopDong.getChiNhanhNganHang() == null
                        || hopDong.getMoTa() == null
                        || hopDong.getNgayLap() == null
                        || hopDong.getNguoiDaiDien() == null
                        || hopDong.getNgayKetThuc() == null
                        || hopDong.getMaSoThue() == null
                        || hopDong.getSoTaiKhoan() == null
                ;
    }

}
