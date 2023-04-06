package com.example.healthiu.entity.table;

import lombok.*;
import org.hibernate.Hibernate;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "user")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private String login;
    @Column
    private String name;

    @Column(unique = true)
    private String email;
    @Column
    private String password;

    @Column
    private Date dateOfBirth;

    @Column
    private String role;
    @Column
    private String imgUrl;
    @Column
    private boolean enabled = false;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return login != null && Objects.equals(login, user.login);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}