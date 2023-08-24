package com.hmcuong.fooddelivery.repository;

import com.hmcuong.fooddelivery.entity.DonDatHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DonDatHangRepository extends JpaRepository<DonDatHang,String> {
    List<DonDatHang> findByMonAns_ThucDon_CuaHang_IdCuaHang(String idCuaHang);


    Optional<List<DonDatHang>> findByTinhTrangDonHang(String tinhTrangDonHang);
}
