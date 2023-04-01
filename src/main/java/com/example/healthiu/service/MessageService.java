package com.example.healthiu.service;

import com.example.healthiu.entity.Message;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface MessageService {
    List<String> findAllBySenderLoginAndRecipientLoginJson(String senderLogin, String recipientLogin) throws JsonProcessingException;

    void sortMessages(List<String> messageList);

    void sortMessageList(List<Message> messageList);

    List<String> findAllMessagesBySenderLoginAndRecipientLogin(String senderLogin, String recipientLogin) throws JsonProcessingException;

    List<Message> findAllMessagesByLogins(String login, String companion);

    Message addNewMessage(String senderLogin, String recipientId, String content);

    Message findMessageById(Long id);

    Message updateMessageStatus(Long id);
}
