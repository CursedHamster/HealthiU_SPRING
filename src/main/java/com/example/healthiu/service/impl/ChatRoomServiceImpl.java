package com.example.healthiu.service.impl;

import com.example.healthiu.entity.ChatRoom;
import com.example.healthiu.entity.DoctorChatRoomRequest;
import com.example.healthiu.entity.UserChatRoomRequest;
import com.example.healthiu.repository.ChatRoomRepository;
import com.example.healthiu.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("chatRoomService")
public class ChatRoomServiceImpl implements ChatRoomService {
    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Override
    public String findRecipientLoginForChatInit(String senderLogin) {
        String recipientLogin = null;
        if (chatRoomRepository.findByUserLogin(senderLogin).isPresent()) {
            recipientLogin = chatRoomRepository.findChatRoomByUserLogin(senderLogin).getDoctorLogin();
        }
        return recipientLogin;
    }

    @Override
    public List<ChatRoom> findAllChatRoomsByDoctorLogin(String doctorLogin) {
        return chatRoomRepository.findAllByDoctorLogin(doctorLogin);
    }

    @Override
    public List<ChatRoom> findAllChatRoomsByUserLogin(String userLogin) {
        return chatRoomRepository.findAllByUserLogin(userLogin);
    }

    @Override
    public boolean checkIfChatRoomExists(String userLogin) {
        return chatRoomRepository.findByUserLogin(userLogin).isPresent();
    }

    @Override
    public void addNewChatRoom(UserChatRoomRequest userChatRoomRequest, DoctorChatRoomRequest doctorChatRoomRequest) {
        ChatRoom chatRoom = new ChatRoom(userChatRoomRequest.getUserLogin(), userChatRoomRequest.getColor(),
                doctorChatRoomRequest.getDoctorLogin(), doctorChatRoomRequest.getColor());
        chatRoomRepository.save(chatRoom);
    }

}
