package com.example.healthiu.service;

import com.example.healthiu.entity.ChatRoom;
import com.example.healthiu.entity.DoctorChatRoomRequest;
import com.example.healthiu.entity.UserChatRoomRequest;

import java.util.List;

public interface ChatRoomService {
    String findRecipientLoginForChatInit(String senderLogin);

    List<ChatRoom> findAllChatRoomsByDoctorLogin(String doctorLogin);

    List<ChatRoom> findAllChatRoomsByUserLogin(String userLogin);

    boolean checkIfChatRoomExists(String userLogin);

    void addNewChatRoom(UserChatRoomRequest userChatRoomRequest, DoctorChatRoomRequest doctorChatRoomRequest);
}
