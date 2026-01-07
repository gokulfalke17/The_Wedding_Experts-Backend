package com.twe.theweddingexperts.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProfessionalProfileResponse {

    private Long id;
    private Long userId;
    private String businessName;
    private String description;
    private Integer experienceYears;
    private String city;
    private String state;
    private Boolean isVerified;
    private Boolean isActive;
}
