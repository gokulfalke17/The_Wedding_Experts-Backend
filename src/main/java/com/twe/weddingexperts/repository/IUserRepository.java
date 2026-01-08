package com.twe.weddingexperts.repository;

import com.twe.weddingexperts.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long> {

    public Optional<User> findByEmail(String email);
    public Optional<User> findByPhone(String phone);
    public boolean existsByEmail(String email);
    public boolean existsByPhone(String phone);
    public Optional<User> findByResetPasswordToken(String token);
    public Optional<User> findByRememberMeToken(String rememberMeToken);

    public Optional<User> findByEmailVerificationToken(String token);


}