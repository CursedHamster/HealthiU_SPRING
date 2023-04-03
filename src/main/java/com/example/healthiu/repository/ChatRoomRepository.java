package com.example.healthiu.repository;

import com.example.healthiu.entity.table.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("chatRoomRepository")
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findByUserLogin(String userLogin);

    List<ChatRoom> findAllByDoctorLogin(String doctorLogin);
    List<ChatRoom> findAllByUserLogin(String userLogin);
}
