package com.example.healthiu.security.verification;

import com.example.healthiu.entity.table.User;
import com.example.healthiu.service.EmailService;
import com.example.healthiu.service.VerificationTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.UUID;

@Component
public class VerificationListener implements
        ApplicationListener<VerificationEvent> {
    private final VerificationTokenService verificationTokenService;
    private final EmailService emailService;

    @Autowired
    public VerificationListener(VerificationTokenService verificationTokenService, EmailService emailService) {
        this.verificationTokenService = verificationTokenService;
        this.emailService = emailService;
    }

    @Override
    public void onApplicationEvent(VerificationEvent event) {
        this.verify(event);
    }

    private void verify(VerificationEvent event) {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();
        System.out.println(event.getSource());
        System.out.println(Locale.UK);
        boolean localeIsUkr = (event.getSource()).equals(Locale.UK);
        boolean hasEmail = event.getEmail() != null;
        String recipientAddress = hasEmail ? event.getEmail() : user.getEmail();
        if (hasEmail) {
            verificationTokenService.createVerificationToken(user, token, event.getEmail());
        } else {
            verificationTokenService.createVerificationToken(user, token);
        }
        String verificationUrl
                = event.getVerificationUrl() + "?token=" + token;
        emailService.sendVerificationLetter(hasEmail, recipientAddress, localeIsUkr, verificationUrl);
    }
}