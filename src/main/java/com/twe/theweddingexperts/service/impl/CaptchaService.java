package com.twe.theweddingexperts.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class CaptchaService {

    @Value("${google.recaptcha.secret}")
    private String secretKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public boolean verifyCaptcha(String captchaToken) {

        if (captchaToken == null || captchaToken.isBlank()) {
            return false;
        }

        String googleUrl =
                "https://www.google.com/recaptcha/api/siteverify"
                        + "?secret=" + secretKey
                        + "&response=" + captchaToken;

        Map<String, Object> response =
                restTemplate.postForObject(googleUrl, null, Map.class);

        if (response == null) {
            return false;
        }

        return Boolean.TRUE.equals(response.get("success"));
    }
}
