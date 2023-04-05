package com.example.healthiu.service;

import com.example.healthiu.entity.table.User;
import com.example.healthiu.entity.table.VerificationToken;

public interface VerificationTokenService {

    VerificationToken getVerificationTokenOfUser(User user);

    VerificationToken getVerificationToken(String VerificationToken);

    void createVerificationToken(User user, String token);

    void deleteVerificationTokenById(Long id);
}
