package com.twe.theweddingexperts.mapper;

import com.twe.theweddingexperts.dto.response.ProfessionalProfileResponse;
import com.twe.theweddingexperts.model.ProfessionalProfile;

public class ProfessionalProfileMapper {

    public static ProfessionalProfileResponse toResponse(
            ProfessionalProfile profile) {

        return ProfessionalProfileResponse.builder()
                .id(profile.getId())
                .userId(profile.getUser().getId())
                .businessName(profile.getBusinessName())
                .description(profile.getDescription())
                .experienceYears(profile.getExperienceYears())
                .city(profile.getCity())
                .state(profile.getState())
                .isVerified(profile.getIsVerified())
                .isActive(profile.getIsActive())
                .build();
    }
}
