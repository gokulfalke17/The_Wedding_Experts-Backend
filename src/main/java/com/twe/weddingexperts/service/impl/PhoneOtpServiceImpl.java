package com.twe.weddingexperts.service.impl;

import com.twe.weddingexperts.exception.BadRequestException;
import com.twe.weddingexperts.model.PhoneOtp;
import com.twe.weddingexperts.model.User;
import com.twe.weddingexperts.repository.IPhoneOtpRepository;
import com.twe.weddingexperts.repository.IUserRepository;
import com.twe.weddingexperts.service.IPhoneOtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PhoneOtpServiceImpl implements IPhoneOtpService {

    private final IPhoneOtpRepository otpRepository;
    private final IUserRepository userRepository;

    @Override
    public void sendOtp(String phone) {

        User user = userRepository.findByPhone(phone)
                .orElseThrow(() -> new BadRequestException("User not found"));

        otpRepository.deleteByPhone(phone);

        String otp = String.valueOf(100000 + new Random().nextInt(900000));

        PhoneOtp phoneOtp = PhoneOtp.builder()
                .phone(phone)
                .otp(otp)
                .expiryTime(LocalDateTime.now().plusMinutes(5))
                .attempts(0)
                .verified(false)
                .build();

        otpRepository.save(phoneOtp);

        System.out.println("=================================");
        System.out.println("PHONE OTP (DEV MODE)");
        System.out.println("OTP   : " + otp);
        System.out.println("PHONE : " + phone);
        System.out.println("=================================");
    }

    @Override
    public String verifyOtp(String otp) {

        PhoneOtp phoneOtp = otpRepository
                .findTopByOtpAndVerifiedFalseOrderByIdDesc(otp)
                .orElseThrow(() -> new BadRequestException("Invalid OTP"));

        if (phoneOtp.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("OTP expired");
        }

        phoneOtp.setVerified(true);
        otpRepository.save(phoneOtp);

        User user = userRepository.findByPhone(phoneOtp.getPhone())
                .orElseThrow(() -> new BadRequestException("User not found"));

        user.setPhoneVerified(true);
        userRepository.save(user);

        // ✅ SAFE DELETE
        otpRepository.findByPhone(phoneOtp.getPhone())
                .ifPresent(otpRepository::delete);

        return "Phone number verified successfully";
    }


    // ✅ RESEND – SAFE & STABLE
    @Override
    public void resendOtpByEmail(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException("User not found"));

        if (user.isPhoneVerified()) {
            throw new BadRequestException("Phone already verified");
        }

        String phone = user.getPhone();
        otpRepository.deleteByPhone(phone);

        String otp = String.valueOf(100000 + new Random().nextInt(900000));

        PhoneOtp phoneOtp = PhoneOtp.builder()
                .phone(phone)
                .otp(otp)
                .expiryTime(LocalDateTime.now().plusMinutes(5))
                .attempts(0)
                .verified(false)
                .build();

        otpRepository.save(phoneOtp);

        System.out.println("=================================");
        System.out.println("RESEND PHONE OTP (DEV MODE)");
        System.out.println("OTP   : " + otp);
        System.out.println("PHONE : " + phone);
        System.out.println("=================================");
    }
}
