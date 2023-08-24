package com.hmcuong.fooddelivery.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "monan")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonAn {
    @Id
    @Column(name = "id_monan")
    private String idMonAn;

    @Column(name = "tenmon")
    private String TenMon;

    @Column(name = "mieutamon")
    private String MieuTaMon;

    @Column(name = "dongia")
    private double DonGia;

    @Column(name = "tinhtrangmon")
    private String TinhTrangMon;

    @Column(name = "tuychon")
    private String TuyChon;

    @Column(name = "soluong",nullable = true)
    private Integer soLuong;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "id_thucdon",
            foreignKey = @ForeignKey(name = "MonAn_ThucDon_Fk")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private ThucDon thucDon;

    @ManyToMany(mappedBy = "monAns")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private List<DonDatHang> donDatHangs = new ArrayList<>();

}
