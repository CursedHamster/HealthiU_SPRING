package com.example.healthiu.repository;

import com.example.healthiu.entity.DoctorChatRoomRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("doctorChatRoomRepository")
public interface DoctorChatRoomRequestRepository extends JpaRepository<DoctorChatRoomRequest, String> {
}
