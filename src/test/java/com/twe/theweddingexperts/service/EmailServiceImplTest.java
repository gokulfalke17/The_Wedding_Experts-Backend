package com.twe.theweddingexperts.service;

import com.twe.theweddingexperts.service.impl.EmailServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@ExtendWith(MockitoExtension.class)
class EmailServiceImplTest {

    @InjectMocks
    private EmailServiceImpl emailService;

    @Test
    void sendEmail_success() {
        assertDoesNotThrow(() ->
                emailService.sendEmailVerification(
                        "test@test.com",
                        "Subject",
                        "Body"));
    }
}
