package com.twe.weddingexperts.mapper;

import com.twe.weddingexperts.dto.response.UserResponse;
import com.twe.weddingexperts.model.User;

public class UserMapper {

    public static UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .emailVerified(user.isEmailVerified())
                .phoneVerified(user.isPhoneVerified())
                .emailVerificationToken(user.getEmailVerificationToken())
                .rememberMeToken(user.getRememberMeToken())
                .build();
    }

    public static UserResponse toResponse(User user, String token) {
        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .emailVerified(user.isEmailVerified())
                .phoneVerified(user.isPhoneVerified())
                .token(token)
                .rememberMeToken(user.getRememberMeToken())
                .build();
    }

}
