package com.example.healthiu.repository;

import com.example.healthiu.entity.table.DoctorChatRoomRequest;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("doctorChatRoomRepository")
public interface DoctorChatRoomRequestRepository extends JpaRepository<DoctorChatRoomRequest, Long> {
    Optional<DoctorChatRoomRequest> findByDoctorLogin(String login);
    @Transactional
    void deleteByDoctorLogin(String login);
}
