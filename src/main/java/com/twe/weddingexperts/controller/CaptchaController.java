package com.twe.weddingexperts.controller;

import com.twe.weddingexperts.common.ApiResponse;
import com.twe.weddingexperts.service.impl.CaptchaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/captcha")
@RequiredArgsConstructor
public class CaptchaController {

    private final CaptchaService captchaService;

    @PostMapping("/verify")
    public ApiResponse<Boolean> verify(@RequestParam String token) {

        boolean result = captchaService.verifyCaptcha(token);

        return ApiResponse.success(
                result ? "Captcha valid" : "Captcha invalid",
                result
        );
    }
}
