package com.example.healthiu.service;

public interface EmailService {
    void sendVerificationLetter(boolean hasEmail, String email, boolean localeIsUkr, String verificationUrl);

    void sendNewChatRoomRequestLetter(String login, String role);
}
