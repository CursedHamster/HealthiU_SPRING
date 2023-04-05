package com.example.healthiu.repository;

import com.example.healthiu.entity.table.User;
import com.example.healthiu.entity.table.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("verificationTokenRepository")
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
    VerificationToken findByToken(String token);
    VerificationToken findByUser(User user);

    void deleteById(Long id);
}
