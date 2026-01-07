package com.twe.theweddingexperts.service;

import com.twe.theweddingexperts.dto.request.CreateProfessionalProfileRequest;
import com.twe.theweddingexperts.dto.request.UpdateProfileRequest;
import com.twe.theweddingexperts.dto.response.ProfessionalProfileResponse;

public interface IProfessionalProfileService {

    ProfessionalProfileResponse createProfile(
            CreateProfessionalProfileRequest request);

    ProfessionalProfileResponse getMyProfile();

    ProfessionalProfileResponse updateProfile(
            CreateProfessionalProfileRequest request);

}
