package com.example.healthiu.service.impl;

import com.example.healthiu.entity.DoctorChatRoomRequest;
import com.example.healthiu.entity.RefreshTokenRequest;
import com.example.healthiu.entity.UserChatRoomRequest;
import com.example.healthiu.repository.DoctorChatRoomRequestRepository;
import com.example.healthiu.repository.RefreshTokenRequestRepository;
import com.example.healthiu.repository.UserChatRoomRequestRepository;
import com.example.healthiu.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service("refreshTokenService")
public class RefreshTokenServiceImpl implements RefreshTokenService {
    @Autowired
    private RefreshTokenRequestRepository refreshTokenRequestRepository;

    @Override
    public boolean checkIfRefreshTokenExists(String username) {
        return refreshTokenRequestRepository.findById(username).isPresent();
    }

    @Override
    public boolean checkIfRefreshTokenIsRight(String username, String refreshToken) {
        return Objects.equals(refreshTokenRequestRepository.findById(username).get().getRefreshToken(), refreshToken);
    }

    @Override
    public List<RefreshTokenRequest> findAllRefreshTokens() {
        return refreshTokenRequestRepository.findAll();
    }

    @Override
    public void addNewRefreshToken(String refreshToken, String username) {
        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest(refreshToken, username);
        refreshTokenRequestRepository.save(refreshTokenRequest);
    }

    @Override
    public void removeRefreshToken(String username) {
        refreshTokenRequestRepository.deleteById(username);
    }
}
