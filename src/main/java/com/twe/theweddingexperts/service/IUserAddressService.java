package com.twe.theweddingexperts.service;

import com.twe.theweddingexperts.dto.request.AddressRequest;
import com.twe.theweddingexperts.dto.response.UserAddressResponse;

import java.util.List;

public interface IUserAddressService {
    public UserAddressResponse addMyAddress(AddressRequest request);

    public UserAddressResponse getMyAddress();

    public UserAddressResponse updateMyAddress(AddressRequest request);

    public void deleteMyAddress();

}
