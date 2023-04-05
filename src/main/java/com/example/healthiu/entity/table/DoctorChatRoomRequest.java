package com.example.healthiu.entity.table;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@Table(name = "doctor_chat_room_request")
@NoArgsConstructor
@AllArgsConstructor
public class DoctorChatRoomRequest {
    @Id
    private String doctorLogin;

    @Column
    private String color;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        DoctorChatRoomRequest that = (DoctorChatRoomRequest) o;
        return doctorLogin != null && Objects.equals(doctorLogin, that.doctorLogin);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
