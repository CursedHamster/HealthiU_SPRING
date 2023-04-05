package com.example.healthiu.service;

import com.example.healthiu.entity.UserData;
import com.example.healthiu.entity.table.User;


public interface UserService {
    User register(UserData user);

    User register(UserData user, String role);

    User saveUser(User user);

    boolean checkUserLoginAndPassword(String login, String password);

    boolean checkIfEnabled(String login);

    int checkChangesCount(UserData userData);

    void updateUserInfo(UserData userData);

    boolean checkIfUserExist(String email);

    boolean checkIfEmailExists(String email);

    User findUserByLogin(String login);

    User findUserByEmail(String email);
}
