package com.example.healthiu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "doctor_chat_room_request")
@NoArgsConstructor
@AllArgsConstructor
public class DoctorChatRoomRequest {
    @Id
    private String doctorLogin;

    @Column
    private String color;
}
