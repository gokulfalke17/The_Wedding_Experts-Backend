package com.twe.theweddingexperts.controller;

import com.twe.theweddingexperts.common.ApiResponse;
import com.twe.theweddingexperts.dto.request.LoginRequest;
import com.twe.theweddingexperts.dto.request.RegisterRequest;
import com.twe.theweddingexperts.dto.response.UserResponse;
import com.twe.theweddingexperts.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

        userService.forgotPassword(email);

        return ApiResponse.success(
                "Password reset email sent successfully",
                null
        );
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


    @GetMapping("/verify-email")
    public ApiResponse<String> verifyEmail(@RequestParam String token) {

        userService.verifyEmail(token);

        return ApiResponse.success(
                "Email verified successfully",
                null
        );
    }


    @PostMapping("/resend-verification-email")
    public ApiResponse<String> resendVerificationEmail(@RequestParam String email) {

        userService.resendEmailVerification(email);

        return ApiResponse.success(
                "Verification email resent successfully",
                null
        );
    }




    //http://localhost:8080/oauth2/authorization/google - Google OAuth2 login URL
    //http://localhost:8080/oauth2/authorization/github - GitHub OAuth2 login URL



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


