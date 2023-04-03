package com.example.healthiu.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class MessageData implements Serializable {
    @NotNull
    @NotEmpty
    @NotBlank
    private String content;

    @NotNull
    private String senderLogin;

    @NotNull
    private String recipientLogin;
}
