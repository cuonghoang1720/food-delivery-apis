package com.hmcuong.fooddelivery.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "khachhang")
@Data
public class KhachHang {
    @Id
    @Column(name = "id_khachhang")
    private String idKhachHang;

    @Column(name = "tenkhachhang")
    private String tenKhachHang;

    @Column(name = "sodienthoai",unique = true)
    private String soDienThoai;

    @Column(name = "diachi")
    private String diaChi;

    @Column(name = "email",unique = true)
    private String email;

    @OneToMany(mappedBy = "khachHang", cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JsonIgnore
    private List<DonDatHang> donDatHangs = new ArrayList<>();
}
