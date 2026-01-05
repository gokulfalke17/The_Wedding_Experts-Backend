package com.twe.theweddingexperts.service.impl;

import com.twe.theweddingexperts.dto.request.LoginRequest;
import com.twe.theweddingexperts.dto.request.RegisterRequest;
import com.twe.theweddingexperts.dto.response.UserResponse;
import com.twe.theweddingexperts.entity.User;
import com.twe.theweddingexperts.enums.UserRole;
import com.twe.theweddingexperts.exception.BadRequestException;
import com.twe.theweddingexperts.exception.ResourceNotFoundException;
import com.twe.theweddingexperts.exception.UnauthorizedException;
import com.twe.theweddingexperts.mapper.UserMapper;
import com.twe.theweddingexperts.repository.IUserRepository;
import com.twe.theweddingexperts.security.jwt.JwtUtil;
import com.twe.theweddingexperts.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public UserResponse register(RegisterRequest request) {
        if (UserRole.ADMIN.name().equalsIgnoreCase(request.getRole())) {
            throw new UnauthorizedException("Admin registration is not allowed");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already registered");
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.valueOf(request.getRole()))
                .build();

        userRepository.save(user);

        return UserMapper.toResponse(user);
    }

    @Override
    public UserResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UnauthorizedException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid credentials");
        }

        if (Boolean.TRUE.equals(request.getRememberMe())) {
            user.setRememberMeToken(UUID.randomUUID().toString());
            user.setRememberMeExpiry(LocalDateTime.now().plusDays(365));
            userRepository.save(user);
        }

        String token = jwtUtil.generateToken(
                user.getId(),
                user.getRole().name()
        );

        return UserMapper.toResponse(user, token);
    }

    @Override
    public String forgotPassword(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException("Email not found"));

        if (user.getRole() == UserRole.ADMIN) {
            throw new UnauthorizedException("Admin password reset is not allowed");
        }

        String resetToken = UUID.randomUUID().toString();
        user.setResetPasswordToken(resetToken);
        user.setResetPasswordExpiry(LocalDateTime.now().plusMinutes(15));
        userRepository.save(user);

        return resetToken;
    }


    @Override
    public void resetPassword(String token, String newPassword) {

        User user = userRepository.findByResetPasswordToken(token)
                .orElseThrow(() -> new BadRequestException("Invalid or expired token"));

        if (user.getRole() == UserRole.ADMIN) {
            throw new UnauthorizedException("Admin password reset is not allowed");
        }

        if (user.getResetPasswordExpiry() == null ||
                user.getResetPasswordExpiry().isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Reset password token has expired");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetPasswordToken(null);
        user.setResetPasswordExpiry(null);
        userRepository.save(user);
    }


    @Override
    public UserResponse loginWithRememberMe(String rememberMeToken) {

        User user = userRepository.findByRememberMeToken(rememberMeToken)
                .orElseThrow(() -> new UnauthorizedException("Invalid remember me token"));

        if (user.getRememberMeExpiry() == null ||
                user.getRememberMeExpiry().isBefore(LocalDateTime.now())) {
            throw new UnauthorizedException("Remember me token expired");
        }

        String jwtToken = jwtUtil.generateToken(
                user.getId(),
                user.getRole().name()
        );

        return UserMapper.toResponse(user, jwtToken);
    }


    @Override
    public List<UserResponse> getUsers() {

        List<UserResponse> userList = userRepository.findAll()
                .stream()
                .map(UserMapper::toResponse)
                .toList();

        if(userList.isEmpty() || userList == null) {
            return null;
        }

        return userList;
    }

    @Override
    public UserResponse getUsers(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found.!"));
        return UserMapper.toResponse(user);
    }

}
