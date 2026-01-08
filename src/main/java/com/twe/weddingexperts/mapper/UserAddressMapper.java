package com.twe.weddingexperts.mapper;

import com.twe.weddingexperts.dto.request.AddressRequest;
import com.twe.weddingexperts.dto.response.UserAddressResponse;
import com.twe.weddingexperts.model.User;
import com.twe.weddingexperts.model.UserAddress;

public class UserAddressMapper {

    private UserAddressMapper() {
    }

    public static UserAddressResponse toResponse(UserAddress address) {

        if (address == null) {
            return null;
        }

        return UserAddressResponse.builder()
                .id(address.getId())
                .addressLine1(address.getAddressLine1())
                .addressLine2(address.getAddressLine2())
                .city(address.getCity())
                .state(address.getState())
                .country(address.getCountry())
                .pincode(address.getPincode())
                .build();
    }

    public static UserAddress toEntity(
            AddressRequest request,
            User user
    ) {

        if (request == null) {
            return null;
        }

        return UserAddress.builder()
                .addressLine1(request.getAddressLine1())
                .addressLine2(request.getAddressLine2())
                .city(request.getCity())
                .state(request.getState())
                .country(request.getCountry())
                .pincode(request.getPincode())
                .user(user)
                .build();
    }

    public static void updateEntity(
            UserAddress address,
            AddressRequest request
    ) {

        if (request == null || address == null) {
            return;
        }

        address.setAddressLine1(request.getAddressLine1());
        address.setAddressLine2(request.getAddressLine2());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setCountry(request.getCountry());
        address.setPincode(request.getPincode());
    }
}
