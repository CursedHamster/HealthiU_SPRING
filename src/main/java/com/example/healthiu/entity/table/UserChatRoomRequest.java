package com.example.healthiu.entity.table;

import lombok.*;
import org.hibernate.Hibernate;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@Table(name = "user_chat_room_request")
@NoArgsConstructor
@AllArgsConstructor
public class UserChatRoomRequest {
    @Id
    private String userLogin;

    @Column
    private String color;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        UserChatRoomRequest that = (UserChatRoomRequest) o;
        return userLogin != null && Objects.equals(userLogin, that.userLogin);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
