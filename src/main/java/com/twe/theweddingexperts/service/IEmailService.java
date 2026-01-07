package com.twe.theweddingexperts.service;

public interface IEmailService {

    void sendEmailVerification(String toEmail, String fullName, String token);

    public void sendResetPasswordEmail(String toEmail, String fullName, String token);
    }
