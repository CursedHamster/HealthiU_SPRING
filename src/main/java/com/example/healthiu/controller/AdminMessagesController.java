package com.example.healthiu.controller;

import com.example.healthiu.entity.table.DoctorChatRoomRequest;
import com.example.healthiu.entity.table.UserChatRoomRequest;
import com.example.healthiu.service.ChatRoomRequestService;
import com.example.healthiu.service.ChatRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;


@RestController
@RequestMapping("/api/admin-messages")
public class AdminMessagesController {
    private final ChatRoomService chatRoomService;
    private final ChatRoomRequestService chatRoomRequestService;

    @Autowired
    public AdminMessagesController(ChatRoomService chatRoomService, ChatRoomRequestService chatRoomRequestService) {
        this.chatRoomService = chatRoomService;
        this.chatRoomRequestService = chatRoomRequestService;
    }


    @GetMapping("/requests")
    public ResponseEntity<Map<String, Object>> getChatRoomRequests() {
        List<UserChatRoomRequest> userChatRoomRequests = chatRoomRequestService.findAllUserChatRoomRequests();
        List<DoctorChatRoomRequest> doctorChatRoomRequests = chatRoomRequestService.findAllDoctorChatRoomRequests();
        Map<String, Object> model = new HashMap<>();
        model.put("user", userChatRoomRequests);
        model.put("doctor", doctorChatRoomRequests);
        return ok(model);
    }

    @PostMapping("/add-chatroom")
    public ResponseEntity<Boolean> addChatRoom(@RequestBody Map<String, Map<String, String>> chatRoomRequests) {
        Map<String, String> user = chatRoomRequests.get("user");
        Map<String, String> doctor = chatRoomRequests.get("doctor");
        UserChatRoomRequest userChatRoomRequest = new UserChatRoomRequest(user.get("userLogin"), user.get("color"));
        DoctorChatRoomRequest doctorChatRoomRequest = new DoctorChatRoomRequest(doctor.get("doctorLogin"), doctor.get("color"));
        if (chatRoomRequestService.checkIfUserChatRoomRequestExists(userChatRoomRequest.getUserLogin())
                && chatRoomRequestService.checkIfDoctorChatRoomRequestExists(doctorChatRoomRequest.getDoctorLogin())) {
            if (chatRoomService.checkIfChatRoomExists(userChatRoomRequest.getUserLogin())) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            chatRoomService.addNewChatRoom(userChatRoomRequest, doctorChatRoomRequest);
            chatRoomRequestService.removeUserChatRoomRequest(userChatRoomRequest.getUserLogin());
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ok(true);
    }
}
