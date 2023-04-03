package com.example.healthiu.service;

import com.example.healthiu.entity.table.DoctorChatRoomRequest;
import com.example.healthiu.entity.table.UserChatRoomRequest;

import java.util.List;

public interface ChatRoomRequestService {
    boolean checkIfUserChatRoomRequestExists(String userLogin);

    boolean checkIfDoctorChatRoomRequestExists(String doctorLogin);

    List<UserChatRoomRequest> findAllUserChatRoomRequests();

    List<DoctorChatRoomRequest> findAllDoctorChatRoomRequests();

    void addNewUserChatRoomRequest(String login, String color);

    void addNewDoctorChatRoomRequest(String login, String color);

    void removeUserChatRoomRequest(String login);

    void removeDoctorChatRoomRequest(String login);
}
