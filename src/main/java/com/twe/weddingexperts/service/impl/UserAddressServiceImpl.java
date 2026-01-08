package com.twe.weddingexperts.service.impl;

import com.twe.weddingexperts.dto.request.AddressRequest;
import com.twe.weddingexperts.dto.response.UserAddressResponse;
import com.twe.weddingexperts.exception.ResourceNotFoundException;
import com.twe.weddingexperts.mapper.UserAddressMapper;
import com.twe.weddingexperts.model.User;
import com.twe.weddingexperts.model.UserAddress;
import com.twe.weddingexperts.repository.IUserAddressRepository;
import com.twe.weddingexperts.repository.IUserRepository;
import com.twe.weddingexperts.service.IUserAddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserAddressServiceImpl implements IUserAddressService {

    private final IUserRepository userRepository;
    private final IUserAddressRepository addressRepository;

    @Override
    public UserAddressResponse addMyAddress(AddressRequest request) {

        Long userId = (Long) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        boolean addressExists = addressRepository
                .findByUserIdAndIsDeletedFalse(userId)
                .isPresent();

        if (addressExists) {
            log.error("Attempt to add address when one already exists for user ID: {}", userId);
            throw new IllegalStateException(
                    "Address already exists for this user. Please update the address instead."
            );
        }

        UserAddress address = addressRepository
                .findByUserIdAndIsDeletedFalse(userId)
                .orElse(UserAddress.builder()
                        .user(user)
                        .isDeleted(false)
                        .build());

        address.setAddressLine1(request.getAddressLine1());
        address.setAddressLine2(request.getAddressLine2());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setCountry(request.getCountry());
        address.setPincode(request.getPincode());

        addressRepository.save(address);

        return UserAddressMapper.toResponse(address);
    }

    @Override
    public UserAddressResponse getMyAddress() {

        Long userId = (Long) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        UserAddress address = addressRepository
                .findByUserIdAndIsDeletedFalse(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found"));

        return UserAddressMapper.toResponse(address);
    }



    @Override
    public UserAddressResponse updateMyAddress(AddressRequest request) {

        Long userId = (Long) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        UserAddress address = addressRepository
                .findByUserIdAndIsDeletedFalse(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found"));

        address.setAddressLine1(request.getAddressLine1());
        address.setAddressLine2(request.getAddressLine2());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setCountry(request.getCountry());
        address.setPincode(request.getPincode());

        addressRepository.save(address);

        return UserAddressMapper.toResponse(address);
    }




    @Override
    public void deleteMyAddress() {

        Long userId = (Long) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();

        UserAddress address = addressRepository
                .findByUserIdAndIsDeletedFalse(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found"));

        address.setIsDeleted(true);

        addressRepository.save(address);
    }






}
