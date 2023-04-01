package com.example.healthiu.repository;

import com.example.healthiu.entity.UserChatRoomRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("userChatRoomRepository")
public interface UserChatRoomRequestRepository extends JpaRepository<UserChatRoomRequest, String> {
}
