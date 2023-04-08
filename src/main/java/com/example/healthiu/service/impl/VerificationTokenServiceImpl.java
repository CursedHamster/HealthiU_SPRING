package com.example.healthiu.service.impl;

import com.example.healthiu.entity.table.User;
import com.example.healthiu.entity.table.VerificationToken;
import com.example.healthiu.repository.VerificationTokenRepository;
import com.example.healthiu.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("verificationTokenService")
public class VerificationTokenServiceImpl implements VerificationTokenService {
    private final VerificationTokenRepository verificationTokenRepository;

    @Autowired
    public VerificationTokenServiceImpl(VerificationTokenRepository verificationTokenRepository) {
        this.verificationTokenRepository = verificationTokenRepository;
    }

    @Override
    public VerificationToken getVerificationTokenOfUser(User user) {
        return verificationTokenRepository.findByUser(user);
    }

    @Override
    public VerificationToken getVerificationToken(String VerificationToken) {
        return verificationTokenRepository.findByToken(VerificationToken);
    }

    @Override
    public void createVerificationToken(User user, String token) {
        VerificationToken verificationToken = getVerificationTokenOfUser(user);
        if (verificationToken != null) {
            verificationTokenRepository.deleteById(verificationToken.getId());
        }
        VerificationToken myToken = new VerificationToken(token, user);
        verificationTokenRepository.save(myToken);
    }

    @Override
    public void createVerificationToken(User user, String token, String email) {
        VerificationToken verificationToken = getVerificationTokenOfUser(user);
        if (verificationToken != null) {
            verificationTokenRepository.deleteById(verificationToken.getId());
        }
        VerificationToken myToken = new VerificationToken(token, user, email);
        verificationTokenRepository.save(myToken);
    }

    @Override
    public void deleteVerificationTokenById(Long id) {
        verificationTokenRepository.deleteById(id);
    }
}
