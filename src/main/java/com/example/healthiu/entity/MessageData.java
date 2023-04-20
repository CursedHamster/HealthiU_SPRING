package com.example.healthiu.entity;

import com.example.healthiu.entity.table.Message;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageData implements Serializable {
    private Long id;
    @NotNull
    @NotEmpty
    @NotBlank
    private String content;

    @NotNull
    private String senderLogin;

    @NotNull
    private String recipientLogin;

    private Timestamp timestamp;

    private String status;

    public MessageData(String content, String senderLogin, String recipientLogin) {
        this.content = content;
        this.senderLogin = senderLogin;
        this.recipientLogin = recipientLogin;
    }

    public MessageData(Message message) {
        this.id = message.getId();
        this.content = message.getContent();
        this.senderLogin = message.getSender().getLogin();
        this.recipientLogin = message.getRecipient().getLogin();
        this.timestamp = message.getTimestamp();
        this.status = message.getStatus();
    }
}
