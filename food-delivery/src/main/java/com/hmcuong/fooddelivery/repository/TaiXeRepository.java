package com.hmcuong.fooddelivery.repository;

import com.hmcuong.fooddelivery.entity.TaiXe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaiXeRepository extends JpaRepository<TaiXe,String> {
}
