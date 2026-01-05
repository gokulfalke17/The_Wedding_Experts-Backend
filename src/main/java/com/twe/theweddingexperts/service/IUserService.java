package com.twe.theweddingexperts.service;

import com.twe.theweddingexperts.dto.request.LoginRequest;
import com.twe.theweddingexperts.dto.request.RegisterRequest;
import com.twe.theweddingexperts.dto.response.UserResponse;
import com.twe.theweddingexperts.entity.User;

import java.util.List;

public interface IUserService {

    public UserResponse register(RegisterRequest request);
    public UserResponse login(LoginRequest request);
    public String forgotPassword(String email);
    public void resetPassword(String token, String newPassword);
    public UserResponse loginWithRememberMe(String rememberMeToken);

    public List<UserResponse> getUsers();
    public UserResponse getUsers(Long id);

}