package com.twe.weddingexperts.config;

import com.twe.weddingexperts.model.User;
import com.twe.weddingexperts.enums.UserRole;
import com.twe.weddingexperts.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class AdminInitializer {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.email}")
    private String adminEmail;

    @Value("${app.admin.password}")
    private String adminPassword;

    @Value("${app.admin.name}")
    private String adminName;

    @Value("${app.admin.phone}")
    private String adminPhone;

    @Bean
    public ApplicationRunner createDefaultAdmin() {
        return args -> {

            boolean adminExists = userRepository.existsByEmail(adminEmail);

            if (!adminExists) {
                User admin = User.builder()
                        .fullName(adminName)
                        .email(adminEmail)
                        .phone(adminPhone)
                        .password(passwordEncoder.encode(adminPassword))
                        .role(UserRole.ADMIN)
                        .build();

                userRepository.save(admin);

                log.info("Default ADMIN created: {}", adminEmail);
            } else {
                log.info("ADMIN already exists");
            }
        };
    }
}
