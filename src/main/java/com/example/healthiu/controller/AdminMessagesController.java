package com.example.healthiu.controller;

import com.example.healthiu.entity.DoctorChatRoomRequestData;
import com.example.healthiu.entity.UserChatRoomRequestData;
import com.example.healthiu.service.ChatRoomRequestService;
import com.example.healthiu.service.ChatRoomService;
import com.example.healthiu.service.UserService;
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
    private final UserService userService;

    @Autowired
    public AdminMessagesController(ChatRoomService chatRoomService, ChatRoomRequestService chatRoomRequestService, UserService userService) {
        this.chatRoomService = chatRoomService;
        this.chatRoomRequestService = chatRoomRequestService;
        this.userService = userService;
    }


    @GetMapping("/requests")
    public ResponseEntity<Map<String, Object>> getChatRoomRequests() {
        List<UserChatRoomRequestData> userChatRoomRequests = chatRoomRequestService.findAllUserChatRoomRequests();
        List<DoctorChatRoomRequestData> doctorChatRoomRequests = chatRoomRequestService.findAllDoctorChatRoomRequests();
        Map<String, Object> model = new HashMap<>();
        model.put("user", userChatRoomRequests);
        model.put("doctor", doctorChatRoomRequests);
        return ok(model);
    }

    @PostMapping("/add-chatroom")
    public ResponseEntity<Boolean> addChatRoom(@RequestBody Map<String, String> chatRoomRequests) {
        String user = chatRoomRequests.get("user");
        String doctor = chatRoomRequests.get("doctor");
        if (chatRoomRequestService.checkIfUserChatRoomRequestExists(user)
                && chatRoomRequestService.checkIfDoctorChatRoomRequestExists(doctor)) {
            if (chatRoomService.checkIfChatRoomExists(user)) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            chatRoomService.addNewChatRoom(chatRoomRequestService.findUserChatRoomRequestByLogin(user),
                    chatRoomRequestService.findDoctorChatRoomRequestByLogin(doctor));
            chatRoomRequestService.removeUserChatRoomRequest(user);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ok(true);
    }
}
