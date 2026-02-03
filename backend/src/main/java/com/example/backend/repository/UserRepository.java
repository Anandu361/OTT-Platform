package com.example.backend.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.backend.models.UserModel;

public interface UserRepository extends JpaRepository<UserModel, Long> {
    UserModel findByEmail(String email);
    UserModel findByToken(String token);
    boolean existsByToken(String token);
    List<UserModel> findByEmailContainingIgnoreCase(String email);

}