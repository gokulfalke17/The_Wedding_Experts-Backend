package com.twe.weddingexperts.service.impl;

import com.twe.weddingexperts.dto.request.ChangePasswordRequest;
import com.twe.weddingexperts.dto.request.LoginRequest;
import com.twe.weddingexperts.dto.request.RegisterRequest;
import com.twe.weddingexperts.dto.request.UpdateProfileRequest;
import com.twe.weddingexperts.dto.response.UserResponse;
import com.twe.weddingexperts.enums.UserRole;
import com.twe.weddingexperts.exception.BadRequestException;
import com.twe.weddingexperts.exception.ResourceNotFoundException;
import com.twe.weddingexperts.exception.UnauthorizedException;
import com.twe.weddingexperts.mapper.UserMapper;
import com.twe.weddingexperts.model.User;
import com.twe.weddingexperts.repository.IUserRepository;
import com.twe.weddingexperts.security.jwt.JwtUtil;
import com.twe.weddingexperts.service.IEmailService;
import com.twe.weddingexperts.service.IPhoneOtpService;
import com.twe.weddingexperts.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final CaptchaService captchaService;

    private final IEmailService emailService;       // FUTURE (Mandrill)
    private final IPhoneOtpService phoneOtpService; // FUTURE (Twilio)

    // ========================= REGISTER =========================
    @Override
    public UserResponse register(RegisterRequest request) {

        if (UserRole.ADMIN.name().equalsIgnoreCase(request.getRole())) {
            throw new UnauthorizedException("Admin registration is not allowed");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already registered");
        }

        if (!captchaService.verifyCaptcha(request.getCaptchaToken())) {
            throw new BadRequestException("Invalid captcha");
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.valueOf(request.getRole()))
                .emailVerified(false)
                .phoneVerified(false)
                .emailVerificationToken(UUID.randomUUID().toString())
                .emailVerificationExpiry(LocalDateTime.now().plusHours(24))
                .build();

        userRepository.save(user);

        // ---------- EMAIL VERIFICATION (DEV MODE) ----------
//        System.out.println("=================================");
//        System.out.println("EMAIL VERIFICATION TOKEN (DEV)");
//        System.out.println("Token : " + user.getEmailVerificationToken());
//        System.out.println("Verify URL:");
//        System.out.println("http://localhost:8080/api/auth/verify-email?token="
//                + user.getEmailVerificationToken());
//        System.out.println("=================================");


        log.info("User registered: {}", user.getEmail());
        log.info("Email verification token: {}", user.getEmailVerificationToken());
        log.info("Phone OTP sent to: {}", user.getPhone());
        log.info("Please verify email and phone to complete registration.");

        // TODO (PROD)
        // emailService.sendEmailVerification(
        //        user.getEmail(),
        //        user.getFullName(),
        //        user.getEmailVerificationToken()
        // );

        // ---------- PHONE OTP (DEV MODE – CORRECT) ----------
        phoneOtpService.sendOtp(user.getPhone());

        return UserMapper.toResponse(user);
    }

    // ========================= LOGIN =========================
    @Override
    public UserResponse login(LoginRequest request) {

        if (!captchaService.verifyCaptcha(request.getCaptchaToken())) {
            throw new UnauthorizedException("Captcha verification failed");
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UnauthorizedException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid credentials");
        }

        if (!user.isEmailVerified()) {
            throw new UnauthorizedException("Email is not verified");
        }

        if (!user.isPhoneVerified()) {
            throw new UnauthorizedException("Phone number is not verified");
        }

        if (Boolean.TRUE.equals(request.getRememberMe())) {
            user.setRememberMeToken(UUID.randomUUID().toString());
            user.setRememberMeExpiry(LocalDateTime.now().plusDays(365));
            userRepository.save(user);
        }

        String jwtToken = jwtUtil.generateToken(
                user.getId(),
                user.getRole().name()
        );

        return UserMapper.toResponse(user, jwtToken);
    }

    // ========================= FORGOT PASSWORD =========================
    @Override
    public void forgotPassword(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException("Email not found"));

        if (user.getRole() == UserRole.ADMIN) {
            throw new UnauthorizedException("Admin password reset is not allowed");
        }

        user.setResetPasswordToken(UUID.randomUUID().toString());
        user.setResetPasswordExpiry(LocalDateTime.now().plusMinutes(15));
        userRepository.save(user);

//        System.out.println("=================================");
//        System.out.println("RESET PASSWORD TOKEN (DEV)");
//        System.out.println(user.getResetPasswordToken());
//        System.out.println("=================================");

        log.info("Password reset requested for: {}", user.getEmail());
        log.info("Reset token: {}", user.getResetPasswordToken());



        // TODO (PROD)
        // emailService.sendResetPasswordEmail(...)
    }

    @Override
    public void resetPassword(String token, String newPassword) {

        User user = userRepository.findByResetPasswordToken(token)
                .orElseThrow(() -> new BadRequestException("Invalid or expired token"));

        if (user.getResetPasswordExpiry().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Reset password token expired");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetPasswordToken(null);
        user.setResetPasswordExpiry(null);
        userRepository.save(user);
    }

    // ========================= REMEMBER ME =========================
    @Override
    public UserResponse loginWithRememberMe(String rememberMeToken) {

        User user = userRepository.findByRememberMeToken(rememberMeToken)
                .orElseThrow(() -> new UnauthorizedException("Invalid remember me token"));

        if (user.getRememberMeExpiry().isBefore(LocalDateTime.now())) {
            throw new UnauthorizedException("Remember me token expired");
        }

        if (!user.isEmailVerified() || !user.isPhoneVerified()) {
            throw new UnauthorizedException("User verification incomplete");
        }

        String jwtToken = jwtUtil.generateToken(
                user.getId(),
                user.getRole().name()
        );

        return UserMapper.toResponse(user, jwtToken);
    }

    // ========================= PROFILE =========================
    @Override
    public UserResponse getMyProfile() {
        Long userId = (Long) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return UserMapper.toResponse(user);
    }

    @Override
    public UserResponse updateMyProfile(UpdateProfileRequest request) {
        Long userId = (Long) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setFullName(request.getFullName());
        user.setPhone(request.getPhone());
        userRepository.save(user);

        return UserMapper.toResponse(user);
    }

    @Override
    public void changePassword(ChangePasswordRequest request) {
        Long userId = (Long) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new UnauthorizedException("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    // ========================= EMAIL VERIFY =========================
    @Override
    public void verifyEmail(String token) {

        User user = userRepository.findByEmailVerificationToken(token)
                .orElseThrow(() -> new BadRequestException("Invalid email verification token"));

        if (user.getEmailVerificationExpiry().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Email verification token expired");
        }

        user.setEmailVerified(true);
        user.setEmailVerificationToken(null);
        user.setEmailVerificationExpiry(null);
        userRepository.save(user);
    }

    @Override
    public void resendEmailVerification(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (user.isEmailVerified()) {
            throw new BadRequestException("Email already verified");
        }

        user.setEmailVerificationToken(UUID.randomUUID().toString());
        user.setEmailVerificationExpiry(LocalDateTime.now().plusHours(24));
        userRepository.save(user);

//        System.out.println("=================================");
//        System.out.println("RESEND EMAIL TOKEN (DEV)");
//        System.out.println(user.getEmailVerificationToken());
//        System.out.println("=================================");


        log.info("Resent email verification to: {}", user.getEmail());
        log.info("New verification token: {}", user.getEmailVerificationToken());
    }

    @Override
    public List<UserResponse> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toResponse)
                .toList();
    }

    @Override
    public UserResponse getUsers(Long id) {
        return userRepository.findById(id)
                .map(UserMapper::toResponse)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
