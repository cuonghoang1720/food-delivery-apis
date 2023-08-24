package com.hmcuong.fooddelivery.repository;

import com.hmcuong.fooddelivery.entity.HopDong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface HopDongRepository extends JpaRepository<HopDong,String> {
    @Transactional
    @Modifying
    @Query("SELECT hd.maSoThue FROM HopDong hd WHERE hd.maSoThue = ?1")
    Optional<String> findByMaSoThue(String maSoThue);

    List<HopDong> findByDoiTac_IdDoiTac(String idDoiTac);
}
