package com.example.healthiu.repository;

import com.example.healthiu.entity.RefreshTokenRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("refreshTokenRequestRepository")
public interface RefreshTokenRequestRepository extends JpaRepository<RefreshTokenRequest, String> {
}
