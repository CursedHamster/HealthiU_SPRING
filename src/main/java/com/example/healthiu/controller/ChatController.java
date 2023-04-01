package com.example.healthiu.controller;

import com.example.healthiu.entity.Message;
import com.example.healthiu.entity.MessageData;
import com.example.healthiu.entity.Role;
import com.example.healthiu.service.ChatRoomRequestService;
import com.example.healthiu.service.ChatRoomService;
import com.example.healthiu.service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ChatController {
    @Autowired
    private MessageService messageService;

    @Autowired
    private ChatRoomService chatRoomService;

    @Autowired
    private ChatRoomRequestService chatRoomRequestService;

    private List<String> chatRoomList = new ArrayList<>();

//    @GetMapping("/chatroom")
//    public String getChat(Model model, HttpServletRequest req, Authentication authentication) throws JsonProcessingException {
//        String role = getRole(authentication);
//        String senderLogin = req.getRemoteUser();
//        String recipientLogin = "anonymous";
//
//        if (role.equals(Role.ADMIN.toString())) {
//            return "redirect:/chatroom/admin";
//        }
//
//        if (role.equals(Role.USER.toString())) {
//            if (chatRoomService.checkIfChatRoomExists(senderLogin)) {
//                recipientLogin = chatRoomService.findRecipientLoginForChatInit(senderLogin);
//            } else {
//                return "redirect:/chatroom/request-chatroom";
//            }
//        }
//
//        if (role.equals(Role.DOCTOR.toString())) {
//            chatRoomList = chatRoomService.findAllChatRoomsByDoctorLoginJson(senderLogin);
//            model.addAttribute("requested_doctor",
//                    chatRoomRequestService.checkIfDoctorChatRoomRequestExists(senderLogin));
//
//        }
//
//        addAttributes(model, role, senderLogin, recipientLogin, chatRoomList);
//
//        return "chatroom";
//    }

//    @GetMapping("/chatroom/user-{login}")
//    public String getChatRoom(@PathVariable("login") String recipientLogin, Model model, HttpServletRequest req,
//                              Authentication authentication) throws JsonProcessingException {
//        String role = getRole(authentication);
//        String senderLogin = req.getRemoteUser();
//        if (role.equals(Role.ADMIN.toString())) {
//            return "redirect:/chatroom/admin";
//        }
//        if (role.equals(Role.USER.toString())) {
//            if (chatRoomService.checkIfChatRoomExists(senderLogin)) {
//                if (!chatRoomService.findRecipientLoginForChatInit(senderLogin).equals(recipientLogin)) {
//                    return "redirect:/chatroom";
//                }
//            } else {
//                return "redirect:/chatroom/request-chatroom";
//            }
//        }
//        if (role.equals(Role.DOCTOR.toString())) {
//            if (!chatRoomService.findAllChatRoomsByDoctorLoginJson(senderLogin).contains(recipientLogin)) {
//                return "redirect:/chatroom";
//            } else {
//                chatRoomList = chatRoomService.findAllChatRoomsByDoctorLoginJson(senderLogin);
//            }
//            model.addAttribute("requested_doctor",
//                    chatRoomRequestService.checkIfDoctorChatRoomRequestExists(senderLogin));
//        }
//        addAttributes(model, role, senderLogin, recipientLogin, chatRoomList);
//        return "chatroom";
//    }
//
//    @GetMapping("/chatroom/request-chatroom")
//    public String getRequestChatroom(Model model, HttpServletRequest req) {
//        String userLogin = req.getRemoteUser();
//        if (chatRoomService.checkIfChatRoomExists(userLogin)) {
//            return "redirect:/chatroom";
//        }
//        model.addAttribute("requested", chatRoomRequestService.checkIfUserChatRoomRequestExists(userLogin));
//        return "request_chatroom";
//    }

//    @GetMapping("/chatroom/request-chatroom/requested")
//    public String requestChatroom(Model model, HttpServletRequest req) {
//        String userLogin = req.getRemoteUser();
//        if (chatRoomService.checkIfChatRoomExists(userLogin)) {
//            return "redirect:/chatroom";
//        }
//        if (!chatRoomRequestService.checkIfUserChatRoomRequestExists(userLogin)) {
//            chatRoomRequestService.addNewUserChatRoomRequest(userLogin);
//            model.addAttribute("requested", chatRoomRequestService.checkIfUserChatRoomRequestExists(userLogin));
//        }
//        return "redirect:/chatroom/request-chatroom";
//    }
//
//    @GetMapping("/chatroom/request-chatroom-doctor")
//    public String requestChatroomDoctor(Model model, HttpServletRequest req) {
//        String doctorLogin = req.getRemoteUser();
//        if (!chatRoomRequestService.checkIfDoctorChatRoomRequestExists(doctorLogin)) {
//            chatRoomRequestService.addNewDoctorChatRoomRequest(doctorLogin);
//            model.addAttribute("requested_doctor",
//                    chatRoomRequestService.checkIfDoctorChatRoomRequestExists(doctorLogin));
//        }
//        return "redirect:/chatroom";
//    }

    @GetMapping("/chatroom/unrequest-chatroom-doctor")
    public String unrequestChatroomDoctor(Model model, HttpServletRequest req) {
        String doctorLogin = req.getRemoteUser();
        if (chatRoomRequestService.checkIfDoctorChatRoomRequestExists(doctorLogin)) {
            chatRoomRequestService.removeDoctorChatRoomRequest(doctorLogin);
            model.addAttribute("requested_doctor",
                    chatRoomRequestService.checkIfDoctorChatRoomRequestExists(doctorLogin));
        }
        return "redirect:/chatroom";
    }

//    @GetMapping("/chatroom/admin")
//    public String getChatRoomAdmin(Model model) {
//        model.addAttribute("userRequestList", chatRoomRequestService.findAllUserLogins());
//        model.addAttribute("doctorRequestList", chatRoomRequestService.findAllDoctorLogins());
//        return "admin_chatroom";
//    }
//
//    @GetMapping("/chatroom/admin/add-chatroom/{userLogin}/{doctorLogin}")
//    public String addChatRoom(@PathVariable String userLogin, @PathVariable String doctorLogin,
//                               Model model) throws Exception {
//        if (chatRoomRequestService.findAllUserLogins().contains(userLogin)
//                && chatRoomRequestService.findAllDoctorLogins().contains(doctorLogin)) {
//            chatRoomService.addNewChatRoom(userLogin, doctorLogin);
//            chatRoomRequestService.removeUserChatRoomRequest(userLogin);
//        } else {
//            throw new Exception("ChatRoom is invalid");
//        }
//        return "admin_chatroom";
//    }

//    @MessageMapping("/message")
//    @SendTo("/chat/messages")
//    public Message addMessage(@Valid MessageData messageData) {
//        return messageService.addNewMessage(
//                messageData.getSenderLogin(), messageData.getRecipientLogin(), messageData.getContent()
//        );
//    }
//
//    private String getRole(Authentication authentication) {
//        String role = authentication.getAuthorities().toString();
//        role = role.replace("[", "");
//        role = role.replace("]", "");
//        return role;
//    }

    private void addAttributes(Model model, String role, String senderLogin, String recipientLogin, List<String> chatRoomList)
            throws JsonProcessingException {
        model.addAttribute("role", role);
        model.addAttribute("senderLogin", senderLogin);
        model.addAttribute("recipientLogin", recipientLogin);
        model.addAttribute("messageList",
                messageService.findAllMessagesBySenderLoginAndRecipientLogin(senderLogin, recipientLogin));
        model.addAttribute("chatRoomList", chatRoomList);
    }

}
