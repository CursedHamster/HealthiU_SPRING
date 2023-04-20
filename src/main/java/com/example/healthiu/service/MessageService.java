package com.example.healthiu.service;

import com.example.healthiu.entity.MessageData;
import com.example.healthiu.entity.table.Message;
import com.example.healthiu.entity.table.User;

import java.util.List;

public interface MessageService {
    List<MessageData> findAllMessagesByLogins(String login, String companion);

    List<MessageData> convertMessageListToMessageDataList(List<Message> messageList);

    Message addNewMessage(User sender, User recipient, String content);

    Message findMessageById(Long id);

    void updateMessageStatus(Long id);

    Long countUnreadMessages(String senderLogin, String recipientLogin);
}
