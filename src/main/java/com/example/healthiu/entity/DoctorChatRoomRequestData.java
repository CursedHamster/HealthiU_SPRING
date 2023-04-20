package com.example.healthiu.entity;

import com.example.healthiu.entity.table.DoctorChatRoomRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorChatRoomRequestData {
    private Long id;
    private String login;
    private String imgUrl;

    public DoctorChatRoomRequestData(DoctorChatRoomRequest doctorChatRoomRequest) {
        this.id = doctorChatRoomRequest.getId();
        this.login = doctorChatRoomRequest.getDoctor().getLogin();
        this.imgUrl = doctorChatRoomRequest.getDoctor().getImgUrl();
    }
}
