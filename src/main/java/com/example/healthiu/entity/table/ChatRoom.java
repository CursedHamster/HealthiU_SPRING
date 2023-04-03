package com.example.healthiu.entity.table;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "chat_room")
@Getter
@Setter
@ToString
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ChatRoom chatRoom = (ChatRoom) o;
        return id != null && Objects.equals(id, chatRoom.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
