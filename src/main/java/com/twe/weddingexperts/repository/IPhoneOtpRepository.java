package com.twe.weddingexperts.repository;

import com.twe.weddingexperts.model.PhoneOtp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IPhoneOtpRepository extends JpaRepository<PhoneOtp, Long> {

    Optional<PhoneOtp> findTopByOtpAndVerifiedFalseOrderByIdDesc(String otp);

    void deleteByPhone(String phone);

    Optional<PhoneOtp> findByPhone(String phone);
}

