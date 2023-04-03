package com.example.healthiu.controller;

import com.example.healthiu.entity.MessageData;
import com.example.healthiu.entity.table.Message;
import com.example.healthiu.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @MessageMapping("/message/{senderLogin}/{recipientLogin}")
    @SendTo({"/chat/messages/{senderLogin}", "/chat/messages/{recipientLogin}"})
    public Message addMessage(@RequestBody MessageData messageData,
                              @DestinationVariable String senderLogin,
                              @DestinationVariable String recipientLogin) {
        if (senderLogin.equals(messageData.getSenderLogin()) && recipientLogin.equals(messageData.getRecipientLogin())
                && messageData.getContent() != null) {
            return messageService.addNewMessage(
                    messageData.getSenderLogin(), messageData.getRecipientLogin(), messageData.getContent()
            );
        }
        return new Message();
    }

    @MessageExceptionHandler
    public void handleException(Throwable exception) {
        System.out.println("Exception occurred: {}" + exception.getMessage());
    }
}
