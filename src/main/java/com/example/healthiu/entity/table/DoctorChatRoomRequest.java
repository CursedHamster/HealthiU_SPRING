package com.example.healthiu.entity.table;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
