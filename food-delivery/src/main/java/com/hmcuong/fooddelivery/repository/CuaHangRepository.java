package com.hmcuong.fooddelivery.repository;

import com.hmcuong.fooddelivery.entity.CuaHang;
import com.hmcuong.fooddelivery.entity.ThucDon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CuaHangRepository extends JpaRepository<CuaHang,String> {
    @Query(value = "select ch from CuaHang ch where ch.idCuaHang = ?1")
    Optional<CuaHang> findCuaHangById(String idCuaHang);

    @Query(value = "select ch from CuaHang ch, DoiTac dt where ch.idCuaHang = ?1 and dt.idDoiTac = ?2 and ch.doiTac.idDoiTac = dt.idDoiTac")
    Optional<CuaHang> findCuaHangByIdDoiTac(String idCuaHang, String idDoiTac);

    @Query(value = "select ch from CuaHang ch where ch.doiTac.idDoiTac = ?1")
    Optional<List<CuaHang>> findCuaHangOfDoiTac(String idDoiTac);

    @Query(value = "UPDATE CuaHang SET tenCuaHang = ?1 WHERE idCuaHang = ?2 ")
    void updateTenCuaHang(String tenCuaHangNew,String idCuaHang);

    @Query(value = "select ch from CuaHang ch where ch.doiTac.loaiAmThuc LIKE %?1%")
    Optional<List<CuaHang>> find_CuaHang_By_LoaiAmThuc(String loaiAmThuc);
}
