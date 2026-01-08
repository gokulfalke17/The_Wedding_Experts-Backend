package com.twe.weddingexperts.service.impl;

import com.twe.weddingexperts.exception.EmailSendException;
import com.twe.weddingexperts.service.IEmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements IEmailService {

    @Value("${mandrill.api.key}")
    private String mandrillApiKey;

    @Value("${mandrill.from.email}")
    private String fromEmail;

    @Value("${app.base-url}")
    private String baseUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public void sendEmailVerification(String toEmail, String fullName, String token) {

        String verifyUrl = baseUrl + "/api/auth/verify-email?token=" + token;

        String htmlContent = """
                <h2>Hello %s,</h2>
                <p>Welcome to <b>TheWeddingExperts</b>.</p>
                <p>Please verify your email by clicking the button below:</p>
                <p>
                    <a href="%s"
                       style="padding:10px 15px;
                              background:#4CAF50;
                              color:white;
                              text-decoration:none;
                              border-radius:5px;">
                       Verify Email
                    </a>
                </p>
                <p>This link will expire in 24 hours.</p>
                <p>If you did not register, please ignore this email.</p>
                """.formatted(fullName, verifyUrl);

        Map<String, Object> message = new HashMap<>();
        message.put("from_email", fromEmail);
        message.put("subject", "Verify your email - TheWeddingExperts");
        message.put("html", htmlContent);
        message.put("to", List.of(
                Map.of(
                        "email", toEmail,
                        "type", "to"
                )
        ));

        Map<String, Object> payload = new HashMap<>();
        payload.put("key", mandrillApiKey);
        payload.put("message", message);

        try {
            restTemplate.postForObject(
                    "https://mandrillapp.com/api/1.0/messages/send.json",
                    payload,
                    String.class
            );
        } catch (Exception ex) {
            throw new EmailSendException("Failed to send verification email");
        }
    }

    @Override
    public void sendResetPasswordEmail(String toEmail, String fullName, String token) {

        String resetUrl =
                baseUrl + "/reset-password?token=" + token;

        String html = """
            <h2>Hello %s,</h2>
            <p>You requested to reset your password.</p>
            <p>
              <a href="%s">Reset Password</a>
            </p>
            <p>This link will expire in 15 minutes.</p>
            """.formatted(fullName, resetUrl);

        // Mandrill send logic (same as verification)
    }

}
