package com.example.healthiu.security.verification;

import com.example.healthiu.entity.table.User;
import com.example.healthiu.service.VerificationTokenService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

@Component
public class VerificationListener implements
        ApplicationListener<VerificationEvent> {
    private final VerificationTokenService verificationTokenService;
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Autowired
    public VerificationListener(VerificationTokenService verificationTokenService, JavaMailSender mailSender, SpringTemplateEngine templateEngine) {
        this.verificationTokenService = verificationTokenService;
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public void onApplicationEvent(VerificationEvent event) {
        this.verify(event);
    }

    private void verify(VerificationEvent event) {
        try {
            User user = event.getUser();
            String token = UUID.randomUUID().toString();
            boolean localeIsUkr = (event.getSource()).equals(Locale.UK);
            String subject = localeIsUkr ? "Підтвердження реєстрації" : "Registration Confirmation";
            String recipientAddress = user.getEmail();
            if (event.getEmail() != null) {
                verificationTokenService.createVerificationToken(user, token, event.getEmail());
                recipientAddress = event.getEmail();
                subject = localeIsUkr ? "Підтвердження зміни електронної пошти" : "New Email Confirmation";
            } else {
                verificationTokenService.createVerificationToken(user, token);
            }
            String verificationUrl
                    = event.getVerificationUrl() + "?token=" + token;
            Map<String, Object> model = new HashMap<>();
            model.put("verificationUrl", verificationUrl);
            model.put("websiteUrl", "http://localhost:5173/");
            Context context = new Context();
            context.setVariables(model);
            String html = templateEngine.process(localeIsUkr ? "email_template_ukr.html" : "email_template_eng.html", context);
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(recipientAddress);
            helper.setSubject(subject);
            helper.setText(html, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
        }
    }
}