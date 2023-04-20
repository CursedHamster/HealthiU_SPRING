package com.example.healthiu.service;

import com.example.healthiu.entity.DoctorChatRoomRequestData;
import com.example.healthiu.entity.UserChatRoomRequestData;
import com.example.healthiu.entity.table.DoctorChatRoomRequest;
import com.example.healthiu.entity.table.User;
import com.example.healthiu.entity.table.UserChatRoomRequest;

import java.util.List;

public interface ChatRoomRequestService {
    UserChatRoomRequest findUserChatRoomRequestByLogin(String userLogin);

    DoctorChatRoomRequest findDoctorChatRoomRequestByLogin(String userLogin);

    boolean checkIfUserChatRoomRequestExists(String userLogin);

    boolean checkIfDoctorChatRoomRequestExists(String doctorLogin);

    List<UserChatRoomRequestData> findAllUserChatRoomRequests();

    List<DoctorChatRoomRequestData> findAllDoctorChatRoomRequests();

    void addNewUserChatRoomRequest(User user);

    void addNewDoctorChatRoomRequest(User doctor);

    void removeUserChatRoomRequest(String login);

    void removeDoctorChatRoomRequest(String login);
}
