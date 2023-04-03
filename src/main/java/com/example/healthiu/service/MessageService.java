package com.example.healthiu.service;

import com.example.healthiu.entity.table.Message;

import java.util.List;

public interface MessageService {

    void sortMessageList(List<Message> messageList);

    List<Message> findAllMessagesByLogins(String login, String companion);

    Message addNewMessage(String senderLogin, String recipientId, String content);

    Message findMessageById(Long id);

    void updateMessageStatus(Long id);
}
