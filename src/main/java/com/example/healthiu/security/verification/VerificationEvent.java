package com.example.healthiu.security.verification;

import com.example.healthiu.entity.table.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class VerificationEvent extends ApplicationEvent {
    private String verificationUrl;
    private User user;
    private String email;

    public VerificationEvent(Object source, String verificationUrl, User user) {
        super(source);
        this.verificationUrl = verificationUrl;
        this.user = user;
    }
    public VerificationEvent(Object source, String verificationUrl, User user, String email) {
        super(source);
        this.verificationUrl = verificationUrl;
        this.user = user;
        this.email = email;
    }
}
