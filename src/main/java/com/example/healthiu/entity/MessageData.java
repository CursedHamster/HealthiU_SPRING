package com.example.healthiu.entity;

import lombok.Data;

import jakarta.validation.constraints.*;
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
