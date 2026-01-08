package com.twe.weddingexperts.service;

public interface IPhoneOtpService {

    void sendOtp(String phone);

    String verifyOtp(String otp);

    void resendOtpByEmail(String email);

}
