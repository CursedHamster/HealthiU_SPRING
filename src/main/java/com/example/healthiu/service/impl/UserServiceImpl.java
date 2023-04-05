package com.example.healthiu.service.impl;

import com.example.healthiu.entity.Role;
import com.example.healthiu.entity.UserData;
import com.example.healthiu.entity.table.User;
import com.example.healthiu.repository.UserRepository;
import com.example.healthiu.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User register(UserData user) {
        return registerUser(user, Role.USER.toString());
    }

    @Override
    public User register(UserData user, String role) {
        return registerUser(user, role);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean checkUserLoginAndPassword(String login, String password) {
        User user = userRepository.findUserByLogin(login);
        return (user != null && login.equals(user.getLogin()) && password.equals(user.getPassword()));
    }

    @Override
    public boolean checkIfEnabled(String login) {
        User user = userRepository.findUserByLogin(login);
        return (user != null && user.isEnabled());
    }

    @Override
    public int checkChangesCount(UserData userData) {
        User user = userRepository.findUserByLogin(userData.getLogin());
        int changeCount = 0;
        if (!user.getName().equals(userData.getName())) {
            changeCount++;
        }
        if (!user.getEmail().equals(userData.getEmail())) {
            changeCount++;
        }
        if (!user.getPassword().equals(userData.getPassword()) && userData.getPassword().length() > 0) {
            changeCount++;
        }
        if (!user.getDateOfBirth().toString().equals(userData.getDateOfBirth().toString())) {
            changeCount++;
        }
        return changeCount;
    }

    @Override
    public void updateUserInfo(UserData userData) {
        User user = userRepository.findUserByLogin(userData.getLogin());
        if (!user.getName().equals(userData.getName())) {
            user.setName(userData.getName());
        }
        if (!user.getEmail().equals(userData.getEmail())) {
            user.setEmail(userData.getEmail());
        }
        if (!user.getPassword().equals(userData.getPassword()) && userData.getPassword().length() > 0) {
            user.setPassword(userData.getPassword());
        }
        if (!user.getDateOfBirth().equals(userData.getDateOfBirth())) {
            user.setDateOfBirth(userData.getDateOfBirth());
        }
        userRepository.save(user);
    }

    @Override
    public boolean checkIfUserExist(String login) {
        return userRepository.findById(login).isPresent();
    }

    @Override
    public boolean checkIfEmailExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public User findUserByLogin(String login) {
        return userRepository.findUserByLogin(login);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }


    private User registerUser(UserData user, String role) {
        User userEntity = new User();
        BeanUtils.copyProperties(user, userEntity);
        userEntity.setRole(role);
        return userRepository.save(userEntity);
    }
}
