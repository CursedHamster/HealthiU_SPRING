package com.example.healthiu.service.impl;

import com.example.healthiu.entity.table.DoctorChatRoomRequest;
import com.example.healthiu.entity.table.User;
import com.example.healthiu.entity.table.UserChatRoomRequest;
import com.example.healthiu.repository.DoctorChatRoomRequestRepository;
import com.example.healthiu.repository.UserChatRoomRequestRepository;
import com.example.healthiu.service.ChatRoomRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("requestChatRoomService")
public class ChatRoomRequestServiceImpl implements ChatRoomRequestService {
    private final UserChatRoomRequestRepository userChatRoomRequestRepository;

    private final DoctorChatRoomRequestRepository doctorChatRoomRequestRepository;

    @Autowired
    public ChatRoomRequestServiceImpl(UserChatRoomRequestRepository userChatRoomRequestRepository, DoctorChatRoomRequestRepository doctorChatRoomRequestRepository) {
        this.userChatRoomRequestRepository = userChatRoomRequestRepository;
        this.doctorChatRoomRequestRepository = doctorChatRoomRequestRepository;
    }

    @Override
    public boolean checkIfUserChatRoomRequestExists(String userLogin) {
        return userChatRoomRequestRepository.findByUserLogin(userLogin).isPresent();
    }

    @Override
    public boolean checkIfDoctorChatRoomRequestExists(String doctorLogin) {
        return doctorChatRoomRequestRepository.findByDoctorLogin(doctorLogin).isPresent();
    }

    @Override
    public List<UserChatRoomRequest> findAllUserChatRoomRequests() {
        return userChatRoomRequestRepository.findAll();
    }

    @Override
    public List<DoctorChatRoomRequest> findAllDoctorChatRoomRequests() {
        return doctorChatRoomRequestRepository.findAll();
    }

    @Override
    public void addNewUserChatRoomRequest(User user) {
        UserChatRoomRequest userChatRoomRequest = new UserChatRoomRequest(user);
        userChatRoomRequestRepository.save(userChatRoomRequest);
    }

    @Override
    public void addNewDoctorChatRoomRequest(User doctor) {
        DoctorChatRoomRequest doctorChatRoomRequest = new DoctorChatRoomRequest(doctor);
        doctorChatRoomRequestRepository.save(doctorChatRoomRequest);
    }

    @Override
    public void removeUserChatRoomRequest(String login) {
        userChatRoomRequestRepository.deleteByUserLogin(login);
    }

    @Override
    public void removeDoctorChatRoomRequest(String login) {
        doctorChatRoomRequestRepository.deleteByDoctorLogin(login);
    }
}
