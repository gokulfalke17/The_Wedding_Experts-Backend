package com.twe.weddingexperts.controller;

import com.twe.weddingexperts.common.ApiResponse;
import com.twe.weddingexperts.dto.request.CreateProfessionalProfileRequest;
import com.twe.weddingexperts.dto.response.ProfessionalProfileResponse;
import com.twe.weddingexperts.service.IProfessionalProfileService;
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
