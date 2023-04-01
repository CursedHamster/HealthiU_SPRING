package com.example.healthiu.rest;

import com.example.healthiu.entity.*;
import com.example.healthiu.service.ChatRoomRequestService;
import com.example.healthiu.service.ChatRoomService;
import com.example.healthiu.service.MessageService;
import com.example.healthiu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.http.ResponseEntity.ok;

@CrossOrigin
@RestController
@RequestMapping("/api/chatroom")
public class ChatRoomController {
    @Autowired
    UserService userService;
    @Autowired
    private ChatRoomService chatRoomService;

    @Autowired
    private ChatRoomRequestService chatRoomRequestService;

    @Autowired
    private MessageService messageService;

    @GetMapping("/requested")
    public ResponseEntity<Boolean> getRequested(@RequestParam(name = "login") String login) {
        String role = userService.findUserByLogin(login).getRole();
        if (Objects.equals(role, Role.USER.toString())) {
            return ok(chatRoomRequestService.checkIfUserChatRoomRequestExists(login));
        } else {
            return ok(chatRoomRequestService.checkIfDoctorChatRoomRequestExists(login));
        }
    }


    @PostMapping("/request-chatroom")
    public ResponseEntity<Boolean> requestChatroom(@RequestParam(name = "login") String login, @RequestParam(name = "color") String color) {
        String role = userService.findUserByLogin(login).getRole();
        if (Objects.equals(role, Role.USER.toString())) {
            if (chatRoomRequestService.checkIfUserChatRoomRequestExists(login)) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            if (chatRoomService.checkIfChatRoomExists(login)) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            }
            chatRoomRequestService.addNewUserChatRoomRequest(login, color);
        } else {
            if (chatRoomRequestService.checkIfDoctorChatRoomRequestExists(login)) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            chatRoomRequestService.addNewDoctorChatRoomRequest(login, color);
        }
        return ok(true);
    }

    @DeleteMapping("/unrequest-chatroom")
    public ResponseEntity<Boolean> unrequestChatroomDoctor(@RequestParam(name = "login") String login) {
        String role = userService.findUserByLogin(login).getRole();
        if (Objects.equals(role, Role.USER.toString())) {
            if (!chatRoomRequestService.checkIfUserChatRoomRequestExists(login) || chatRoomService.checkIfChatRoomExists(login)) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            chatRoomRequestService.removeUserChatRoomRequest(login);
        } else {
            if (!chatRoomRequestService.checkIfDoctorChatRoomRequestExists(login)) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            chatRoomRequestService.removeDoctorChatRoomRequest(login);
        }
        return ok(true);
    }

    @GetMapping("/chatrooms")
    public ResponseEntity<List<ChatRoom>> getChatRooms(@RequestParam(name = "login") String login) {
        String role = userService.findUserByLogin(login).getRole();
        List<ChatRoom> chatRoomList = new ArrayList<>();
        if (role.equals(Role.USER.toString())) {
            chatRoomList = chatRoomService.findAllChatRoomsByUserLogin(login);
        }
        if (role.equals(Role.DOCTOR.toString())) {
            chatRoomList = chatRoomService.findAllChatRoomsByDoctorLogin(login);
        }
        chatRoomList = chatRoomList
                .stream().peek(
                        chatRoom -> chatRoom.setUnreadMessagesCount(
                                messageService.findAllMessagesByLogins(chatRoom.getUserLogin(), chatRoom.getDoctorLogin())
                                        .stream().filter(
                                                message -> message.getRecipientLogin().equals(login)
                                                        && message.getStatus().equals(MessageStatus.UNREAD.toString())).count()
                        )
                )
                .collect(Collectors.toList());

        return ok(chatRoomList);
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getMessages(@RequestParam(name = "login") String login, @RequestParam(name = "companion") String companion) {
        return ok(messageService.findAllMessagesByLogins(login, companion));
    }

    @PutMapping("/message/update-status")
    public void updateMessageStatus(@RequestParam(name = "id") Long id) {
        if (!Objects.equals(messageService.findMessageById(id).getStatus(), MessageStatus.READ.toString())) {
            messageService.updateMessageStatus(id);
        }
    }
}
