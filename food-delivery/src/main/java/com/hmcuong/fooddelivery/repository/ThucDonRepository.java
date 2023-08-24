package com.hmcuong.fooddelivery.repository;

import com.hmcuong.fooddelivery.entity.ThucDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ThucDonRepository extends JpaRepository<ThucDon,String> {
    Optional<ThucDon> findByIdThucDon(String idThucDon);

    @Query(value = "SELECT td FROM ThucDon td WHERE td.cuaHang.idCuaHang = ?1")
    Optional<ThucDon> findByIdCuaHang(String idCuaHang);

    @Query(value = "SELECT td FROM ThucDon td WHERE td.cuaHang.doiTac.idDoiTac = ?1 and td.cuaHang.idCuaHang = ?2 ")
    Optional<ThucDon> find_ThucDon_Of_DoiTac_And_CuaHang(String idDoiTac, String idCuaHang);

    @Query(value = "select td from ThucDon td where td.cuaHang.doiTac.idDoiTac = ?1")
    Optional<List<ThucDon>> find_ThucDon_Of_DoiTac(String idDoiTac);

    @Query(value = "select td from ThucDon td where td.cuaHang.idCuaHang = ?1 and td.idThucDon = ?2")
    Optional<Object> find_ThuDon_By_CuaHang(String idCuaHang, String idThucDon);

    @Query(value = "select td from ThucDon td where td.cuaHang.doiTac.idDoiTac = ?1 and td.cuaHang.idCuaHang = ?2 and td.idThucDon = ?3")
    Optional<ThucDon> find_ThucDon_By_DoiTac_CuaHang(String idDoiTac, String idCuaHang, String idThucDon);
}
