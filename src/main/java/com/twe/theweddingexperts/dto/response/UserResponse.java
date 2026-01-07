package com.twe.theweddingexperts.dto.response;

import com.twe.theweddingexperts.enums.UserRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {

    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private UserRole role;

    private String token;
    private String rememberMeToken;

    private String emailVerificationToken;

    private boolean emailVerified;
    private boolean phoneVerified;
}
