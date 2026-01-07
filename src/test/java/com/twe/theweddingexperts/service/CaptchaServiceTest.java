package com.twe.theweddingexperts.service;

import com.twe.theweddingexperts.exception.BadRequestException;
import com.twe.theweddingexperts.service.impl.CaptchaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(MockitoExtension.class)
class CaptchaServiceTest {

    @InjectMocks
    private CaptchaService captchaService;

    @Test
    void verifyCaptcha_invalidToken() {
        assertThrows(BadRequestException.class,
                () -> captchaService.verifyCaptcha(""));
    }
}
