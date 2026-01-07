package com.twe.theweddingexperts.mapper;

import com.twe.theweddingexperts.dto.response.UserResponse;
import com.twe.theweddingexperts.model.User;

public class UserMapper {

    public static UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .emailVerificationToken(user.getEmailVerificationToken())
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
                .token(token)
                .rememberMeToken(user.getRememberMeToken())
                .build();
    }

}
