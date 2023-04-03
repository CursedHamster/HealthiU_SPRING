package com.example.healthiu.repository;

import com.example.healthiu.entity.table.UserChatRoomRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("userChatRoomRepository")
public interface UserChatRoomRequestRepository extends JpaRepository<UserChatRoomRequest, String> {
}
