package com.example.healthiu.repository;

import com.example.healthiu.entity.table.DoctorChatRoomRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("doctorChatRoomRepository")
public interface DoctorChatRoomRequestRepository extends JpaRepository<DoctorChatRoomRequest, String> {
}
