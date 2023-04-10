package com.example.healthiu.service.impl;

import com.example.healthiu.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
import java.util.Map;

@Service("emailService")
public class EmailServiceImpl implements EmailService {
    private final static String WEBSITE_URL = "https://healthiu.netlify.app/";
    private final static String HEALTHIU_EMAIL = "healthiu.mail@gmail.com";
    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender, SpringTemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    @Override
    public void sendVerificationLetter(boolean hasEmail, String email, boolean localeIsUkr, String verificationUrl) {
        String subject;
        if (hasEmail) {
            subject = localeIsUkr ? "Підтвердження зміни електронної пошти" : "New Email Confirmation";
        } else {
            subject = localeIsUkr ? "Підтвердження реєстрації" : "Registration Confirmation";
        }
        Map<String, Object> model = new HashMap<>();
        model.put("verificationUrl", verificationUrl);
        model.put("websiteUrl", WEBSITE_URL);
        Context context = new Context();
        context.setVariables(model);
        String template = localeIsUkr ? "email_template_ukr.html" : "email_template_eng.html";
        try {
            sendEmailMessage(template, context, email, subject);
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void sendNewChatRoomRequestLetter(String login, String role) {
        String subject = "New Chatroom Request";
        Map<String, Object> model = new HashMap<>();
        model.put("url", WEBSITE_URL + "admin-messages");
        model.put("login", login);
        model.put("role", role);
        Context context = new Context();
        context.setVariables(model);
        String template = "new_request_template_eng.html";
        try {
            sendEmailMessage(template, context, HEALTHIU_EMAIL, subject);
        } catch (MessagingException e) {
            System.out.println(e.getMessage());
        }
    }


    public void sendEmailMessage(String template, Context context, String recipientAddress, String subject) throws MessagingException {
        String html = templateEngine.process(template, context);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(recipientAddress);
        helper.setSubject(subject);
        helper.setText(html, true);
        mailSender.send(message);
    }
}
