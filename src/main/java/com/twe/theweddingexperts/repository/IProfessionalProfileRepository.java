package com.twe.theweddingexperts.repository;

import com.twe.theweddingexperts.model.ProfessionalProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IProfessionalProfileRepository
        extends JpaRepository<ProfessionalProfile, Long> {

    Optional<ProfessionalProfile> findByUserId(Long userId);

    boolean existsByUserId(Long userId);
}
