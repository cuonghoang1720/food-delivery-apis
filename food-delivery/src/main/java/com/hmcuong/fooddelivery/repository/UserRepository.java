package com.hmcuong.fooddelivery.repository;

import com.hmcuong.fooddelivery.entity.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    public Optional<User> findByUserName(String username);
    public User findByVerificationCode(String verificationCode);

    public Optional<User> findByEmail(String email);
}
