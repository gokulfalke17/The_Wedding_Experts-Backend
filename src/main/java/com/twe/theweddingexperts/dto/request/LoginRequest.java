package com.twe.theweddingexperts.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequest {

    private String email;
    private String password;

    @NotNull(message = "Please select remember me option (true or false)")
    private Boolean rememberMe;
}
