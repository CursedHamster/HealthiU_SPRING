package com.example.healthiu.rest;

import com.example.healthiu.entity.DoctorChatRoomRequest;
import com.example.healthiu.entity.Role;
import com.example.healthiu.entity.UserChatRoomRequest;
import com.example.healthiu.service.ChatRoomRequestService;
import com.example.healthiu.service.ChatRoomService;
import com.example.healthiu.service.MessageService;
import com.example.healthiu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

import static org.springframework.http.ResponseEntity.ok;

@CrossOrigin
@RestController
public class HelloController {

    @Autowired
    UserService userService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ChatRoomService chatRoomService;

    @Autowired
    private ChatRoomRequestService chatRoomRequestService;

    private List<String> chatRoomList = new ArrayList<>();
}