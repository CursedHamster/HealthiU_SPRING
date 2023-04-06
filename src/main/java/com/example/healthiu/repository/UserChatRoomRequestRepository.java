package com.example.healthiu.repository;

import com.example.healthiu.entity.table.UserChatRoomRequest;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("userChatRoomRepository")
public interface UserChatRoomRequestRepository extends JpaRepository<UserChatRoomRequest, Long> {
    Optional<UserChatRoomRequest> findByUserLogin(String login);
    @Transactional
    void deleteByUserLogin(String login);
}
