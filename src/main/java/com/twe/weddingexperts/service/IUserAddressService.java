package com.twe.weddingexperts.service;

import com.twe.weddingexperts.dto.request.AddressRequest;
import com.twe.weddingexperts.dto.response.UserAddressResponse;

public interface IUserAddressService {
    public UserAddressResponse addMyAddress(AddressRequest request);

    public UserAddressResponse getMyAddress();

    public UserAddressResponse updateMyAddress(AddressRequest request);

    public void deleteMyAddress();

}
