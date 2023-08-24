package com.hmcuong.fooddelivery.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hmcuong.fooddelivery.enums.HoatDongEnum;
import com.hmcuong.fooddelivery.enums.TinhTrangCuaHangEnum;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cuahang")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CuaHang {
    @Id
    @Column(name = "id_cuahang")
    private String idCuaHang;

    @Column(name = "tencuahang")
    private String tenCuaHang;

    @Column(name = "hoatdong")
    @Enumerated(EnumType.STRING)  // Specify enum type mapping
    private HoatDongEnum hoatDong;

    @Column(name = "tinhtrang")
    @Enumerated(EnumType.STRING)
    private TinhTrangCuaHangEnum tinhTrang;

    @Column(name = "diachi")
    private String diaChi;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "id_doitac",  // Use the actual column name in the database
            foreignKey = @ForeignKey(name = "CuaHang_DoiTac_Fk")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private DoiTac doiTac;

    public boolean hasNullDataBeforeRegister(CuaHang cuaHang) {
        return
                cuaHang.getIdCuaHang() == null
                        || cuaHang.getHoatDong() == null
                        || cuaHang.getTenCuaHang() == null
                        || cuaHang.getDiaChi() == null
                        || cuaHang.getTinhTrang() == null
                ;
    }
}
