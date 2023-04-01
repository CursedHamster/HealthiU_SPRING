package com.example.healthiu.entity;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

public class UserData implements Serializable {

    private String login;

    private String name;

    private String email;

    private String password;

    private String confirmPassword;

    private Date dateOfBirth;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public UserData() {
    }

    public UserData(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public UserData(String login, String name, String email, String password, String confirmPassword, Date dateOfBirth) {
        this.login = login;
        this.name = name;
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.dateOfBirth = dateOfBirth;
    }
}
