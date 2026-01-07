package com.twe.theweddingexperts.controller;

import com.twe.theweddingexperts.common.ApiResponse;
import com.twe.theweddingexperts.dto.request.ChangePasswordRequest;
import com.twe.theweddingexperts.dto.request.UpdateProfileRequest;
import com.twe.theweddingexperts.dto.response.UserResponse;
import com.twe.theweddingexperts.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final IUserService userService;

    @GetMapping("/me")
    public ApiResponse<UserResponse> getMyProfile() {
        return ApiResponse.success(
                "User profile fetched successfully",
                userService.getMyProfile()
        );
    }

    @PutMapping("/me")
    public ApiResponse<UserResponse> updateMyProfile( @RequestBody UpdateProfileRequest request) {
        return ApiResponse.success(
                "User profile updated successfully",
                userService.updateMyProfile(request)
        );
    }

    @PatchMapping("/me/password")
    public ApiResponse<Void> changePassword(@RequestBody ChangePasswordRequest request) {
        return  ApiResponse.success(
                "Password changed successfully",
                null
        );
    }


}

