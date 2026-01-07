package com.twe.theweddingexperts.service.impl;

import com.twe.theweddingexperts.dto.request.CreateProfessionalProfileRequest;
import com.twe.theweddingexperts.dto.response.ProfessionalProfileResponse;
import com.twe.theweddingexperts.enums.UserRole;
import com.twe.theweddingexperts.exception.BadRequestException;
import com.twe.theweddingexperts.exception.ResourceNotFoundException;
import com.twe.theweddingexperts.exception.UnauthorizedException;
import com.twe.theweddingexperts.mapper.ProfessionalProfileMapper;
import com.twe.theweddingexperts.model.ProfessionalProfile;
import com.twe.theweddingexperts.model.User;
import com.twe.theweddingexperts.repository.IProfessionalProfileRepository;
import com.twe.theweddingexperts.repository.IUserRepository;
import com.twe.theweddingexperts.service.IProfessionalProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfessionalProfileServiceImpl
        implements IProfessionalProfileService {

    private final IProfessionalProfileRepository profileRepository;
    private final IUserRepository userRepository;

    private Long getLoggedInUserId() {
        return (Long) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
    }

    @Override
    public ProfessionalProfileResponse createProfile(
            CreateProfessionalProfileRequest request) {

        Long userId = getLoggedInUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (user.getRole() != UserRole.PROFESSIONAL) {
            throw new BadRequestException("Only professionals can create profile");
        }

        if (profileRepository.existsByUserId(userId)) {
            throw new BadRequestException("Profile already exists");
        }

        ProfessionalProfile profile = ProfessionalProfile.builder()
                .user(user)
                .businessName(request.getBusinessName())
                .description(request.getDescription())
                .experienceYears(request.getExperienceYears())
                .city(request.getCity())
                .state(request.getState())
                .build();

        profileRepository.save(profile);

        return ProfessionalProfileMapper.toResponse(profile);
    }

    @Override
    public ProfessionalProfileResponse getMyProfile() {

        Long userId = getLoggedInUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (user.getRole() != UserRole.PROFESSIONAL) {
            throw new UnauthorizedException("Only professionals can access this profile");
        }

        ProfessionalProfile profile = profileRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found"));

        return ProfessionalProfileMapper.toResponse(profile);
    }


    @Override
    public ProfessionalProfileResponse updateProfile(
            CreateProfessionalProfileRequest request) {

        Long userId = getLoggedInUserId();

        ProfessionalProfile profile =
                profileRepository.findByUserId(userId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException("Profile not found"));

        profile.setBusinessName(request.getBusinessName());
        profile.setDescription(request.getDescription());
        profile.setExperienceYears(request.getExperienceYears());
        profile.setCity(request.getCity());
        profile.setState(request.getState());

        profileRepository.save(profile);

        return ProfessionalProfileMapper.toResponse(profile);
    }

}
