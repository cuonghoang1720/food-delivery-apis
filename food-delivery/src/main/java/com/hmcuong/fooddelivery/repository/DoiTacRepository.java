package com.hmcuong.fooddelivery.repository;

import com.hmcuong.fooddelivery.entity.DoiTac;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoiTacRepository extends JpaRepository<DoiTac,String> {

    @Query("SELECT DT FROM DoiTac DT WHERE DT.email = ?1")
    public Optional<DoiTac> findByEmail(String email);

    public Optional<DoiTac> findByIdDoiTac(String idDoiTac);
}
