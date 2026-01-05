package com.twe.theweddingexperts.controller;

import com.twe.theweddingexperts.common.ApiResponse;
import com.twe.theweddingexperts.dto.request.LoginRequest;
import com.twe.theweddingexperts.dto.request.RegisterRequest;
import com.twe.theweddingexperts.dto.response.UserResponse;
import com.twe.theweddingexperts.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IUserService userService;

    @PostMapping("/register")
    public ApiResponse<UserResponse> register(@RequestBody RegisterRequest request) {
        return ApiResponse.success(
                "User registered successfully",
                userService.register(request)
        );
    }

    @PostMapping("/login")
    public ApiResponse<UserResponse> login(@Valid @RequestBody LoginRequest request) {
        return ApiResponse.success(
                "Login successful",
                userService.login(request)
        );
    }


    @PostMapping("/forgot-password")
    public ApiResponse<String> forgotPassword(@RequestParam String email) {
        String token = userService.forgotPassword(email);
        return ApiResponse.success("Reset password token generated", token);
    }

    @PostMapping("/reset-password")
    public ApiResponse<Void> resetPassword(@RequestParam String token,
                                           @RequestParam String newPassword) {
        userService.resetPassword(token, newPassword);
        return ApiResponse.success("Password reset successfully", null);
    }

    @PostMapping("/login/remember-me")
    public ApiResponse<UserResponse> loginWithRememberMe(
            @RequestParam String rememberMeToken) {

        return ApiResponse.success(
                "Login successful using remember me",
                userService.loginWithRememberMe(rememberMeToken)
        );
    }

   /* @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<List<UserResponse>> getAllUsers() {

        return ApiResponse.success(
                "All users fetched successfully",
                userService.getUsers()
        );
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<UserResponse> getUserById(@PathVariable Long userId) {

        return ApiResponse.success(
                "User fetched successfully",
                userService.getUsers(userId)
        );
    }*/

}


