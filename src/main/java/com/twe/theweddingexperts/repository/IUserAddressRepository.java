package com.twe.theweddingexperts.repository;

import com.twe.theweddingexperts.model.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserAddressRepository extends JpaRepository<UserAddress, Long> {

    public Optional<UserAddress> findByUserId(Long userId);

    public boolean existsByUserId(Long userId);

    public void deleteByUserId(Long userId);

    Optional<UserAddress> findByUserIdAndIsDeletedFalse(Long userId);

}
