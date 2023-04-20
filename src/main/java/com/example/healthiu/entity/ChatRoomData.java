package com.example.healthiu.entity;

import com.example.healthiu.entity.table.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomData {
    private Long id;
    private UserData user;
    private UserData doctor;
    private Long unreadMessagesCount;

    public ChatRoomData(ChatRoom chatRoom) {
        this.id = chatRoom.getId();
        this.user = new UserData(chatRoom.getUser());
        this.doctor = new UserData(chatRoom.getDoctor());
        this.unreadMessagesCount = chatRoom.getUnreadMessagesCount();
    }
}
