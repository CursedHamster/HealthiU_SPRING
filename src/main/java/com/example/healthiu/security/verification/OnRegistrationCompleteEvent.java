package com.example.healthiu.security.verification;

import com.example.healthiu.entity.table.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

@Getter
@Setter
public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private String verificationUrl;
    private User user;

    public OnRegistrationCompleteEvent(Object source, String verificationUrl, User user) {
        super(source);
        this.verificationUrl = verificationUrl;
        this.user = user;
    }
}
