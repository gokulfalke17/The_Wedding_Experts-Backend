package com.twe.theweddingexperts.controller;

import com.twe.theweddingexperts.common.ApiResponse;
import com.twe.theweddingexperts.dto.request.AddressRequest;
import com.twe.theweddingexperts.dto.response.UserAddressResponse;
import com.twe.theweddingexperts.service.IUserAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/address")
@RequiredArgsConstructor
public class UserAddressController {

    private final IUserAddressService addressService;

    @GetMapping
    public ApiResponse<UserAddressResponse> getMyAddress() {
        return ApiResponse.success(
                "Address fetched successfully",
                addressService.getMyAddress()
        );
    }

    @PostMapping
    public ApiResponse<UserAddressResponse> addOrUpdateAddress(
            @RequestBody AddressRequest request) {

        return ApiResponse.success(
                "Address saved successfully",
                addressService.addMyAddress(request)
        );
    }

    @PutMapping
    public ApiResponse<UserAddressResponse> updateMyAddress(
            @RequestBody AddressRequest request) {

        return ApiResponse.success(
                "Address updated successfully",
                addressService.updateMyAddress(request)
        );
    }

    @DeleteMapping
    public ApiResponse<Void> deleteMyAddress() {

        addressService.deleteMyAddress();

        return ApiResponse.success(
                "Address deleted successfully",
                null
        );
    }
}
