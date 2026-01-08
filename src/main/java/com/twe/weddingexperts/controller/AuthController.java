package com.twe.weddingexperts.controller;

import com.twe.weddingexperts.common.ApiResponse;
import com.twe.weddingexperts.dto.request.LoginRequest;
import com.twe.weddingexperts.dto.request.RegisterRequest;
import com.twe.weddingexperts.dto.response.UserResponse;
import com.twe.weddingexperts.service.IPhoneOtpService;
import com.twe.weddingexperts.service.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IUserService userService;
    private final IPhoneOtpService phoneOtpService;


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

        return ApiResponse.success(
                "Password reset successfully",
                null
        );
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

    @PostMapping("/verify-phone-otp")
    public ApiResponse<String> verifyPhoneOtp(@RequestParam String otp) {

        String message = phoneOtpService.verifyOtp(otp);

        return ApiResponse.success(message, null);
    }






    @PostMapping("/resend-phone-otp")
    public ApiResponse<String> resendPhoneOtp(@RequestParam String email) {
        phoneOtpService.resendOtpByEmail(email);
        return ApiResponse.success("OTP resent successfully", null);
    }




    /*
    // OTP is already sent during registration,
    // so this API is NOT required currently.
    // Keep it commented for future use.

    @PostMapping("/send-phone-otp")
    public ApiResponse<String> sendPhoneOtp(@RequestParam String phone) {

        phoneOtpService.sendOtp(phone);

        return ApiResponse.success(
                "OTP generated successfully (DEV MODE)",
                null
        );
    }
    */

    // OAuth URLs (keep as reference)
    // http://localhost:8080/oauth2/authorization/google
    // http://localhost:8080/oauth2/authorization/github



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


