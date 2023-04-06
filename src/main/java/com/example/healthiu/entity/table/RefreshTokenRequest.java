package com.example.healthiu.entity.table;

import lombok.*;
import org.hibernate.Hibernate;

import jakarta.persistence.*;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Table(name = "refresh_token")
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenRequest {
    @Column
    private String refreshToken;
    @Id
    private String username;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RefreshTokenRequest that = (RefreshTokenRequest) o;
        return username != null && Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}