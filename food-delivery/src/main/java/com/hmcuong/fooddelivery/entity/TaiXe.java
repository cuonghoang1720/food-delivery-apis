package com.hmcuong.fooddelivery.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "taixe")
@Data
public class TaiXe {
    @Id
    @Column(name = "id_taixe")
    private String idTaiXe;

    @Column(name = "tentaixe")
    private String tenTaiXe;

    @Column(name = "chungminhnhandan")
    private String chungMinhNhanDan;

    @Column(name = "sodienthoai")
    private String SoDienThoai;

    @Column(name = "biensoxe")
    private String bienSoXe;

    @Column(name = "thanhpho")
    private String thanhPho;

    @Column(name = "quan_huyen")
    private String quanHuyen;

    @Column(name = "email")
    private String email;

    @Column(name = "sotaikhoan")
    private String soTaiKhoan;

    @Column(name = "nganhang")
    private String nganHang;

    @Column(name = "chinhanhnganhang")
    private String chiNhanhNganHang;

    @Column(name = "phithechan")
    private double phiTheChan;

    @OneToMany(mappedBy = "taiXe",cascade = CascadeType.ALL)
    @JsonBackReference
    private List<DonDatHang> donDatHangs = new ArrayList<>();
}
