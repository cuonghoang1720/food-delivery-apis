package com.hmcuong.fooddelivery.repository;

import com.hmcuong.fooddelivery.entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang,String> {
}
