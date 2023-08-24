package com.hmcuong.fooddelivery.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "dondathang")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DonDatHang{
    @Id
    @Column(name = "id_dondathang")
    private String idDonDatHang;

    @Column(name = "tinhtrangdonhang")
    private String tinhTrangDonHang;

    @Column(name = "diachigiaohang")
    private String diaChiGiaoHang;

    @Column(name = "phivanchuyen")
    private double phiVanChuyen;

    @Column(name = "tongtien")
    private double tongTien;

    @Column(name = "ngaylap")
    private LocalDateTime ngayLap;

    @ManyToOne
    @JoinColumn(
            name = "id_khachhang",
            nullable = false,
            referencedColumnName = "id_khachhang",
            foreignKey = @ForeignKey(name = "dondathang_khachhang_fk")
    )
    @JsonIgnore
    private KhachHang khachHang;

    @ManyToOne
    @JoinColumn(
            name = "id_taixe",
            nullable = true,
            referencedColumnName = "id_taixe",
            foreignKey = @ForeignKey(name = "dondathang_taixe_fk")
    )
    @JsonIgnore
    private TaiXe taiXe;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "chitietdonhang",
            joinColumns = @JoinColumn(name = "id_dondathang"),
            inverseJoinColumns = @JoinColumn(name = "id_monan")
    )
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private List<MonAn> monAns = new ArrayList<>();
}
