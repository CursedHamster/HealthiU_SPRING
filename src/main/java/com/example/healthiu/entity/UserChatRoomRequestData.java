package com.example.healthiu.entity;

import com.example.healthiu.entity.table.UserChatRoomRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserChatRoomRequestData {
    private Long id;
    private String login;
    private String imgUrl;

    public UserChatRoomRequestData(UserChatRoomRequest userChatRoomRequest) {
        this.id = userChatRoomRequest.getId();
        this.login = userChatRoomRequest.getUser().getLogin();
        this.imgUrl = userChatRoomRequest.getUser().getImgUrl();
    }
}
