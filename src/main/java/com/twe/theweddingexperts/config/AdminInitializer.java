package com.twe.theweddingexperts.config;

import com.twe.theweddingexperts.entity.User;
import com.twe.theweddingexperts.enums.UserRole;
import com.twe.theweddingexperts.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
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

                System.out.println("Default ADMIN created: " + adminEmail);
            } else {
                System.out.println("ADMIN already exists");
            }
        };
    }
}
