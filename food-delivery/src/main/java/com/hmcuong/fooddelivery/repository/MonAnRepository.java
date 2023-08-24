package com.hmcuong.fooddelivery.repository;

import com.hmcuong.fooddelivery.entity.MonAn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MonAnRepository extends JpaRepository<MonAn,String> {
    @Query(value = "select ma from MonAn ma where ma.thucDon.idThucDon = ?1")
    List<MonAn> findByThucDon(String idThucDon);

    @Query(value = "select ma from MonAn ma where ma.thucDon.idThucDon = ?1 and ma.idMonAn = ?2")
    Optional<Object> find_MonAn_By_ThucDon(String idThucDon, String idMonAn);

    @Query(value =
            "select ma " +
            "from MonAn ma " +
            "where " +
                    "ma.thucDon.cuaHang.doiTac.idDoiTac = ?1 " +
                    "and ma.thucDon.cuaHang.idCuaHang = ?2 " +
                    "and ma.thucDon.idThucDon = ?3 " +
                    "and ma.idMonAn = ?4"
    )
    Optional<MonAn> find_MonAn_By_DoiTac_CuaHang_ThucDon(String idDoiTac, String idCuaHang, String idThucDon, String idMonAn);

    @Query(value = "SELECT ma FROM MonAn ma WHERE ma.TenMon LIKE %?1%")
    Optional<List<MonAn>> find_MonAn_By_TenMonAn(String tenMonAn);

}
