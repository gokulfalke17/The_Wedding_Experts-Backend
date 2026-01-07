package com.twe.theweddingexperts.service;

import com.twe.theweddingexperts.dto.request.LoginRequest;
import com.twe.theweddingexperts.dto.request.RegisterRequest;
import com.twe.theweddingexperts.exception.BadRequestException;
import com.twe.theweddingexperts.model.User;
import com.twe.theweddingexperts.repository.IUserRepository;
import com.twe.theweddingexperts.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void registerUser_success() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@test.com");
        request.setPassword("123456");

        when(passwordEncoder.encode(anyString()))
                .thenReturn("encodedPass");

        userService.register(request);

        verify(userRepository).save(any());
    }

    @Test
    void registerUser_emailAlreadyExists() {
        when(userRepository.existsByEmail(anyString()))
                .thenReturn(true);

        RegisterRequest request = new RegisterRequest();
        request.setEmail("test@test.com");

        assertThrows(BadRequestException.class,
                () -> userService.register(request));
    }

    @Test
    void login_success() {
        User user = new User();
        user.setPassword("encoded");

        when(userRepository.findByEmail(anyString()))
                .thenReturn(Optional.of(user));
        when(passwordEncoder.matches(any(), any()))
                .thenReturn(true);

        assertDoesNotThrow(() ->
                userService.login(new LoginRequest()));
    }

    protected void mockSecurityContext(Long userId) {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userId);

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);
    }
}
