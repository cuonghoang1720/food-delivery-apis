package com.hmcuong.fooddelivery.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "thucdon")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThucDon {
    @Id
    @Column(name = "id_thucdon")
    private String idThucDon;

    @Column(name = "tenthucdon")
    private String TenThucDon;

    @Column(name = "mota")
    private String MoTa;

    @OneToOne
    @JoinColumn(
            name = "id_cuahang",
            foreignKey = @ForeignKey(name = "ThucDon_CuaHang_Fk")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private CuaHang cuaHang;

    @OneToMany(
            mappedBy = "thucDon"
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private List<MonAn> monAns = new ArrayList<>();

    public boolean hasNullDataBeforeRegister(ThucDon thucDon) {
        return
            thucDon.getIdThucDon() == null
            || thucDon.getTenThucDon() == null
            || thucDon.getMoTa() == null
        ;
    }
}
