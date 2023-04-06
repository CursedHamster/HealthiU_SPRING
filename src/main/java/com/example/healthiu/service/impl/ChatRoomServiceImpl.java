package com.example.healthiu.service.impl;

import com.example.healthiu.entity.table.ChatRoom;
import com.example.healthiu.entity.table.DoctorChatRoomRequest;
import com.example.healthiu.entity.table.UserChatRoomRequest;
import com.example.healthiu.repository.ChatRoomRepository;
import com.example.healthiu.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("chatRoomService")
public class ChatRoomServiceImpl implements ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    @Autowired
    public ChatRoomServiceImpl(ChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
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
        ChatRoom chatRoom = new ChatRoom(userChatRoomRequest.getUser(), doctorChatRoomRequest.getDoctor());
        chatRoomRepository.save(chatRoom);
    }

}
