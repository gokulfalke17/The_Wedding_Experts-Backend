package com.twe.theweddingexperts.repository;

import com.twe.theweddingexperts.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByPhone(String phone);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    Optional<User> findByResetPasswordToken(String token);
    Optional<User> findByRememberMeToken(String rememberMeToken);

}