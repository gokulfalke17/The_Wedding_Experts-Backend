package com.twe.theweddingexperts.service.impl;

import com.twe.theweddingexperts.dto.request.AddressRequest;
import com.twe.theweddingexperts.dto.response.UserAddressResponse;
import com.twe.theweddingexperts.exception.ResourceNotFoundException;
import com.twe.theweddingexperts.mapper.UserAddressMapper;
import com.twe.theweddingexperts.model.User;
import com.twe.theweddingexperts.model.UserAddress;
import com.twe.theweddingexperts.repository.IUserAddressRepository;
import com.twe.theweddingexperts.repository.IUserRepository;
import com.twe.theweddingexperts.service.IUserAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

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
