package com.twe.theweddingexperts.controller;

import com.twe.theweddingexperts.common.ApiResponse;
import com.twe.theweddingexperts.dto.request.CreateProfessionalProfileRequest;
import com.twe.theweddingexperts.dto.response.ProfessionalProfileResponse;
import com.twe.theweddingexperts.service.IProfessionalProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/professionals")
@RequiredArgsConstructor
@PreAuthorize("hasRole('PROFESSIONAL')")
public class ProfessionalProfileController {

    private final IProfessionalProfileService profileService;

    @PostMapping
    public ApiResponse<ProfessionalProfileResponse> createProfile(
            @RequestBody CreateProfessionalProfileRequest request) {

        return ApiResponse.success(
                "Professional profile created",
                profileService.createProfile(request)
        );
    }

    @GetMapping("/me")
    public ApiResponse<ProfessionalProfileResponse> getMyProfile() {

        return ApiResponse.success(
                "Professional profile fetched",
                profileService.getMyProfile()
        );
    }

    @PutMapping("/me")
    public ApiResponse<ProfessionalProfileResponse> updateProfile(
            @RequestBody CreateProfessionalProfileRequest request) {

        return ApiResponse.success(
                "Professional profile updated",
                profileService.updateProfile(request)
        );
    }


}
