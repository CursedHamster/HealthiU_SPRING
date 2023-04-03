package com.example.healthiu.service;

import com.example.healthiu.entity.table.ChatRoom;
import com.example.healthiu.entity.table.DoctorChatRoomRequest;
import com.example.healthiu.entity.table.UserChatRoomRequest;

import java.util.List;

public interface ChatRoomService {
    List<ChatRoom> findAllChatRoomsByDoctorLogin(String doctorLogin);

    List<ChatRoom> findAllChatRoomsByUserLogin(String userLogin);

    boolean checkIfChatRoomExists(String userLogin);

    void addNewChatRoom(UserChatRoomRequest userChatRoomRequest, DoctorChatRoomRequest doctorChatRoomRequest);
}
