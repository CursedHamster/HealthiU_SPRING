package com.example.healthiu.service;

import com.example.healthiu.entity.table.User;
import com.example.healthiu.entity.UserData;


public interface UserService {
    void register(UserData user);

    void register(UserData user, String role);

    boolean checkUserLoginAndPassword(String login, String password);

    int checkChangesCount(UserData userData);

    void updateUserInfo(UserData userData);

    boolean checkIfUserExist(String email);

    boolean checkIfEmailExists(String email);

    User findUserByLogin(String login);

    User findUserByEmail(String email);
}
