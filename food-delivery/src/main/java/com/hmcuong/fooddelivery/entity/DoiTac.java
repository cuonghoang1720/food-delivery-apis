package com.hmcuong.fooddelivery.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "doitac")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoiTac {

    @Id
    @Column(name = "id_doitac")
    private String idDoiTac;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "tenquan")
    private String tenQuan;

    @Column(name = "nguoidaidien")
    private String nguoiDaiDien;

    @Column(name = "thanhpho")
    private String thanhPho;

    @Column(name = "quan_huyen")
    private String quanHuyen;

    @Column(name = "soluongchinhanh")
    private int soLuongChiNhanh;

    @Column(name = "soluongdonhangdukienmoingay")
    private int soLuongDonHangDuKienMoiNgay;

    @Column(name = "loaiamthuc")
    private String loaiAmThuc;

    @Column(name="diachikinhdoanh")
    private String diaChiKinhDoanh;

    @Column(name = "sodienthoai",unique = true)
    private String soDienThoai;

    @OneToMany(mappedBy = "doiTac",fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private List<HopDong> hopDongs = new ArrayList<>();

    @OneToMany( mappedBy = "doiTac")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private List<CuaHang> cuaHangs = new ArrayList<>();

    public boolean hasNullDataBeforeRegister(DoiTac doiTac){
        return
            doiTac.getIdDoiTac() == null
            || doiTac.getEmail() == null
            || doiTac.getCuaHangs() == null
            || doiTac.getLoaiAmThuc() == null
            || doiTac.getThanhPho() == null
            || doiTac.getQuanHuyen() == null
            || doiTac.getSoDienThoai() == null
            || doiTac.getDiaChiKinhDoanh() == null
            || doiTac.getTenQuan() == null;
    }
}
