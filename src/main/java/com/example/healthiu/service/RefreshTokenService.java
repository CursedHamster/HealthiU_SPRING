package com.example.healthiu.service;

import com.example.healthiu.entity.RefreshTokenRequest;

import java.util.List;

public interface RefreshTokenService {
    boolean checkIfRefreshTokenExists(String username);
    boolean checkIfRefreshTokenIsRight(String username, String refreshToken);
    List<RefreshTokenRequest> findAllRefreshTokens();
    void addNewRefreshToken(String refreshToken, String username);
    void removeRefreshToken(String username);
}
