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

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
}
