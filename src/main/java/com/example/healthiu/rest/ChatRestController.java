package com.example.healthiu.rest;

import com.example.healthiu.entity.MessageStatus;
import com.example.healthiu.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class ChatRestController {
    @Autowired
    private MessageService messageService;

    @GetMapping("/chatroom/update-status/{id}")
    public void updateMessageStatus(@PathVariable Long id) {
        if (!Objects.equals(messageService.findMessageById(id).getStatus(), MessageStatus.READ.toString())) {
            messageService.updateMessageStatus(id);
        }
    }

    @GetMapping("/chatroom/check-status/{id}")
    public boolean checkMessageStatus(@PathVariable Long id) {
        return (Objects.equals(messageService.findMessageById(id).getStatus(), MessageStatus.READ.toString()));
    }
}
