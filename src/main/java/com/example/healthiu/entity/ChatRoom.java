package com.example.healthiu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "chat_room")
@Data
@NoArgsConstructor
public class ChatRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String userLogin;
    @Column
    private String userColor;

    @Column
    private String doctorLogin;

    @Column
    private String doctorColor;

    private Long unreadMessagesCount;

    public ChatRoom(String userLogin, String userColor, String doctorLogin, String doctorColor) {
        this.userLogin = userLogin;
        this.userColor = userColor;
        this.doctorLogin = doctorLogin;
        this.doctorColor = doctorColor;
    }

    public Long getUnreadMessagesCount() {
        return unreadMessagesCount;
    }

    public void setUnreadMessagesCount(Long unreadMessagesCount) {
        this.unreadMessagesCount = unreadMessagesCount;
    }
}
