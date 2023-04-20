package com.example.healthiu.service.impl;

import com.example.healthiu.entity.DoctorChatRoomRequestData;
import com.example.healthiu.entity.UserChatRoomRequestData;
import com.example.healthiu.entity.table.DoctorChatRoomRequest;
import com.example.healthiu.entity.table.User;
import com.example.healthiu.entity.table.UserChatRoomRequest;
import com.example.healthiu.repository.DoctorChatRoomRequestRepository;
import com.example.healthiu.repository.UserChatRoomRequestRepository;
import com.example.healthiu.service.ChatRoomRequestService;
import com.example.healthiu.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("requestChatRoomService")
public class ChatRoomRequestServiceImpl implements ChatRoomRequestService {
    private final UserChatRoomRequestRepository userChatRoomRequestRepository;
    private final DoctorChatRoomRequestRepository doctorChatRoomRequestRepository;
    private final EmailService emailService;

    @Autowired
    public ChatRoomRequestServiceImpl(UserChatRoomRequestRepository userChatRoomRequestRepository, DoctorChatRoomRequestRepository doctorChatRoomRequestRepository, EmailService emailService) {
        this.userChatRoomRequestRepository = userChatRoomRequestRepository;
        this.doctorChatRoomRequestRepository = doctorChatRoomRequestRepository;
        this.emailService = emailService;
    }

    @Override
    public UserChatRoomRequest findUserChatRoomRequestByLogin(String userLogin) {
        return userChatRoomRequestRepository.findByUserLogin(userLogin).orElseThrow();
    }

    @Override
    public DoctorChatRoomRequest findDoctorChatRoomRequestByLogin(String userLogin) {
        return doctorChatRoomRequestRepository.findByDoctorLogin(userLogin).orElseThrow();
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
    public List<UserChatRoomRequestData> findAllUserChatRoomRequests() {
        List<UserChatRoomRequestData> userChatRoomRequestDataList = new ArrayList<>();
        List<UserChatRoomRequest> userChatRoomRequestList = userChatRoomRequestRepository.findAll();
        userChatRoomRequestList.forEach(userChatRoomRequest ->
                userChatRoomRequestDataList.add(new UserChatRoomRequestData(userChatRoomRequest)));
        return userChatRoomRequestDataList;
    }

    @Override
    public List<DoctorChatRoomRequestData> findAllDoctorChatRoomRequests() {
        List<DoctorChatRoomRequestData> doctorChatRoomRequestDataList = new ArrayList<>();
        List<DoctorChatRoomRequest> doctorChatRoomRequestList = doctorChatRoomRequestRepository.findAll();
        doctorChatRoomRequestList.forEach(doctorChatRoomRequest ->
                doctorChatRoomRequestDataList.add(new DoctorChatRoomRequestData(doctorChatRoomRequest)));
        return doctorChatRoomRequestDataList;
    }

    @Override
    public void addNewUserChatRoomRequest(User user) {
        UserChatRoomRequest userChatRoomRequest = new UserChatRoomRequest(user);
        userChatRoomRequestRepository.save(userChatRoomRequest);
        emailService.sendNewChatRoomRequestLetter(user.getLogin(), user.getRole());
    }

    @Override
    public void addNewDoctorChatRoomRequest(User doctor) {
        DoctorChatRoomRequest doctorChatRoomRequest = new DoctorChatRoomRequest(doctor);
        doctorChatRoomRequestRepository.save(doctorChatRoomRequest);
        emailService.sendNewChatRoomRequestLetter(doctor.getLogin(), doctor.getRole());
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
