package com.example.healthiu.service.impl;

import com.example.healthiu.entity.MessageStatus;
import com.example.healthiu.entity.table.Message;
import com.example.healthiu.repository.MessageRepository;
import com.example.healthiu.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service("messageService")
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public void sortMessageList(List<Message> messageList) {
        messageList.sort(Comparator.comparing(Message::getId));
    }

    @Override
    public List<Message> findAllMessagesByLogins(String login, String companion) {
        List<Message> messageList = new ArrayList<>();
        if (messageRepository.findBySenderLoginAndRecipientLogin(login, companion).isPresent()) {
            messageList.addAll(messageRepository.findAllBySenderLoginAndRecipientLogin(login, companion));
        }
        if (messageRepository.findBySenderLoginAndRecipientLogin(companion, login).isPresent()) {
            messageList.addAll(messageRepository.findAllBySenderLoginAndRecipientLogin(companion, login));
        }
        sortMessageList(messageList);
        return messageList;
    }

    @Override
    public Message addNewMessage(String senderLogin, String recipientId, String content) {
        Message message = new Message(senderLogin, recipientId, content);
        messageRepository.save(message);
        return message;
    }

    @Override
    public Message findMessageById(Long id) {
        return messageRepository.findMessageById(id);
    }

    @Override
    public void updateMessageStatus(Long id) {
        Message message = messageRepository.findMessageById(id);
        message.setStatus(MessageStatus.READ.toString());
        messageRepository.save(message);
    }
}
