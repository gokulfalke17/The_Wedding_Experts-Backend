package com.twe.theweddingexperts.entity;

import com.twe.theweddingexperts.enums.AccountStatus;
import com.twe.theweddingexperts.enums.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String phone;

    @Column(nullable = false)
    private boolean phoneVerified = false;

    @Column(nullable = false)
    private boolean emailVerified = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;   // ADMIN / CUSTOMER / PROFESSIONAL

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatus status; // ACTIVE / BLOCKED / DELETED

    @Column(name = "remember_me_token", length = 500)
    private String rememberMeToken;

    @Column(name = "remember_me_expiry")
    private LocalDateTime rememberMeExpiry;

    @Column(name = "reset_password_token", length = 500)
    private String resetPasswordToken;

    @Column(name = "reset_password_expiry")
    private LocalDateTime resetPasswordExpiry;

    @Column(nullable = false, updatable = false)
    @Schema(type = "string", format = "date-time")
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @Schema(type = "string", format = "date-time")
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        status = AccountStatus.ACTIVE;
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
