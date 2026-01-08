package com.twe.weddingexperts.dto.request;

import lombok.Data;

@Data
public class CreateProfessionalProfileRequest {

    private String businessName;
    private String description;
    private Integer experienceYears;
    private String city;
    private String state;
}
