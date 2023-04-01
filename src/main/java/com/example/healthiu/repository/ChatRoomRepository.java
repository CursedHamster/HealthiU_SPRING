package com.example.healthiu.repository;

import com.example.healthiu.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("chatRoomRepository")
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    ChatRoom findChatRoomByDoctorLoginAndUserLogin(String doctorLogin, String userLogin);
    ChatRoom findChatRoomByUserLogin(String userLogin);

    Optional<ChatRoom> findByDoctorLoginAndUserLogin(String doctorLogin, String userLogin);
    Optional<ChatRoom> findByUserLogin(String userLogin);

    List<ChatRoom> findAllByDoctorLogin(String doctorLogin);
    List<ChatRoom> findAllByUserLogin(String userLogin);
}
