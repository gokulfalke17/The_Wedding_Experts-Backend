package com.twe.weddingexperts.service;

import com.twe.weddingexperts.dto.request.CreateProfessionalProfileRequest;
import com.twe.weddingexperts.dto.response.ProfessionalProfileResponse;

public interface IProfessionalProfileService {

    ProfessionalProfileResponse createProfile(
            CreateProfessionalProfileRequest request);

    ProfessionalProfileResponse getMyProfile();

    ProfessionalProfileResponse updateProfile(
            CreateProfessionalProfileRequest request);

}
