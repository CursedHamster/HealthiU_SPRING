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
@Table(name = "user_chat_room_request")
@NoArgsConstructor
@AllArgsConstructor
public class UserChatRoomRequest {
    @Id
    private String userLogin;

    @Column
    private String color;
}
