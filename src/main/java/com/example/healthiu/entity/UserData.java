package com.example.healthiu.entity;

import com.example.healthiu.entity.table.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserData implements Serializable {

    private String login;

    private String name;

    private String email;

    private String password;

    private String confirmPassword;

    private Date dateOfBirth;
    private String role;
    private String imgUrl;

    public UserData(User user) {
        this.login = user.getLogin();
        this.name = user.getName();
        this.email = user.getEmail();
        this.dateOfBirth = user.getDateOfBirth();
        this.role = user.getRole();
        this.imgUrl = user.getImgUrl();
    }
}
