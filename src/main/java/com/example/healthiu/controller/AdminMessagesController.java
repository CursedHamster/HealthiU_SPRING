package com.example.healthiu.controller;

import com.example.healthiu.entity.table.DoctorChatRoomRequest;
import com.example.healthiu.entity.table.UserChatRoomRequest;
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
        List<UserChatRoomRequest> userChatRoomRequests = chatRoomRequestService.findAllUserChatRoomRequests();
        List<DoctorChatRoomRequest> doctorChatRoomRequests = chatRoomRequestService.findAllDoctorChatRoomRequests();
        Map<String, Object> model = new HashMap<>();
        model.put("user", userChatRoomRequests);
        model.put("doctor", doctorChatRoomRequests);
        return ok(model);
    }

    @PostMapping("/add-chatroom")
    public ResponseEntity<Boolean> addChatRoom(@RequestBody Map<String, String> chatRoomRequests) {
        String user = chatRoomRequests.get("user");
        String doctor = chatRoomRequests.get("doctor");
        UserChatRoomRequest userChatRoomRequest = new UserChatRoomRequest(userService.findUserByLogin(user));
        DoctorChatRoomRequest doctorChatRoomRequest = new DoctorChatRoomRequest(userService.findUserByLogin(doctor));
        if (chatRoomRequestService.checkIfUserChatRoomRequestExists(userChatRoomRequest.getUser().getLogin())
                && chatRoomRequestService.checkIfDoctorChatRoomRequestExists(doctorChatRoomRequest.getDoctor().getLogin())) {
            if (chatRoomService.checkIfChatRoomExists(userChatRoomRequest.getUser().getLogin())) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            chatRoomService.addNewChatRoom(userChatRoomRequest, doctorChatRoomRequest);
            chatRoomRequestService.removeUserChatRoomRequest(userChatRoomRequest.getUser().getLogin());
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return ok(true);
    }
}
