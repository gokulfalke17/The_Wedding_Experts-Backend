package com.twe.theweddingexperts.service;

import com.twe.theweddingexperts.dto.request.AddressRequest;
import com.twe.theweddingexperts.model.UserAddress;
import com.twe.theweddingexperts.repository.IUserAddressRepository;
import com.twe.theweddingexperts.service.impl.UserAddressServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserAddressServiceImplTest {

    @Mock
    private IUserAddressRepository addressRepository;

    @InjectMocks
    private UserAddressServiceImpl addressService;

    @Test
    void addAddress_success() {
        AddressRequest request = new AddressRequest();
        request.setCity("Athens");

        addressService.addMyAddress(request);

        verify(addressRepository).save(any(UserAddress.class));
    }

    @Test
    void getAddresses_success() {
        when(addressRepository.findAll())
                .thenReturn(List.of(new UserAddress()));

        assertFalse(addressService.getMyAddress()==null);
    }

    protected void mockSecurityContext(Long userId) {
        Authentication authentication = mock(Authentication.class);
        when(authentication.getPrincipal()).thenReturn(userId);

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        SecurityContextHolder.setContext(securityContext);
    }
}
