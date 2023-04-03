package com.example.healthiu.entity;

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
}
