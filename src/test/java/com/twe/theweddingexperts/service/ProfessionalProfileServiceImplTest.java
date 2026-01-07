package com.twe.theweddingexperts.service;

import com.twe.theweddingexperts.dto.request.CreateProfessionalProfileRequest;
import com.twe.theweddingexperts.dto.request.UpdateProfileRequest;
import com.twe.theweddingexperts.model.ProfessionalProfile;
import com.twe.theweddingexperts.repository.IProfessionalProfileRepository;
import com.twe.theweddingexperts.service.impl.ProfessionalProfileServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProfessionalProfileServiceImplTest {

    @Mock
    private IProfessionalProfileRepository repository;

    @InjectMocks
    private ProfessionalProfileServiceImpl service;

    @Test
    void createProfile_success() {
        CreateProfessionalProfileRequest request =
                new CreateProfessionalProfileRequest();
        request.setBusinessName("Studio X");

        service.createProfile(request);

        verify(repository).save(any(ProfessionalProfile.class));
    }

    @Test
    void updateProfile_success() {
        // given
        CreateProfessionalProfileRequest request = new CreateProfessionalProfileRequest();

        when(repository.findById(anyLong()))
                .thenReturn(Optional.of(new ProfessionalProfile()));

        // when / then
        assertDoesNotThrow(() ->
                service.updateProfile(request)
        );
    }
}
