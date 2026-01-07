package com.twe.theweddingexperts.service;

import com.twe.theweddingexperts.exception.BadRequestException;
import com.twe.theweddingexperts.service.impl.CaptchaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CaptchaServiceTest {

    @InjectMocks
    private CaptchaService captchaService;

    @Test
    void verifyCaptcha_invalidToken() {
        assertThrows(BadRequestException.class,
                () -> captchaService.verifyCaptcha(""));
    }

    protected void mockSecurityContext(Long userId) {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userId);

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);
    }
}
