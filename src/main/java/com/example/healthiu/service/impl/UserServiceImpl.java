package com.example.healthiu.service.impl;

import com.example.healthiu.entity.Role;
import com.example.healthiu.entity.UserData;
import com.example.healthiu.entity.table.User;
import com.example.healthiu.repository.UserRepository;
import com.example.healthiu.service.StorageService;
import com.example.healthiu.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final StorageService storageService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, StorageService storageService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.storageService = storageService;
        this.passwordEncoder = passwordEncoder;
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
        return (user != null && login.equals(user.getLogin()) && passwordEncoder.matches(password, user.getPassword()));
    }

    @Override
    public boolean checkIfEnabled(String login) {
        User user = userRepository.findUserByLogin(login);
        return (user != null && user.isEnabled());
    }

    @Override
    public boolean checkEmailChange(UserData userData) {
        return !userRepository.findUserByLogin(userData.getLogin()).getEmail().equals(userData.getEmail());
    }

    @Override
    public int checkChangesCount(UserData userData) {
        User user = userRepository.findUserByLogin(userData.getLogin());
        int changeCount = 0;
        if (!user.getName().equals(userData.getName())) {
            changeCount++;
        }
        if (!passwordEncoder.matches(userData.getPassword(), user.getPassword()) && userData.getPassword().length() > 0) {
            changeCount++;
        }
        if (!user.getDateOfBirth().toString().equals(userData.getDateOfBirth().toString())) {
            changeCount++;
        }
        if ((user.getImgUrl() == null && userData.getImgUrl() != null)
                || (user.getImgUrl() != null && !user.getImgUrl().equals(userData.getImgUrl()))) {
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
        if (!passwordEncoder.matches(userData.getPassword(), user.getPassword()) && userData.getPassword().length() > 0) {
            user.setPassword(passwordEncoder.encode(userData.getPassword()));
        }
        if (!user.getDateOfBirth().equals(userData.getDateOfBirth())) {
            user.setDateOfBirth(userData.getDateOfBirth());
        }
        if ((user.getImgUrl() == null && userData.getImgUrl() != null) || (user.getImgUrl() != null
                && !user.getImgUrl().equals(userData.getImgUrl()))) {
            if (user.getImgUrl() != null) {
                try {
                    storageService.deleteFile(user.getImgUrl());
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
            user.setImgUrl(userData.getImgUrl());
        }
        userRepository.save(user);
    }

    @Override
    public boolean checkIfUserExists(String login) {
        return userRepository.findUserByLoginIgnoreCase(login).isPresent();
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

    @Override
    public void deleteUser(String login) {
        userRepository.deleteById(login);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    private User registerUser(UserData user, String role) {
        User userEntity = new User();
        BeanUtils.copyProperties(user, userEntity);
        userEntity.setPassword(passwordEncoder.encode(user.getPassword()));
        userEntity.setRole(role);
        return userRepository.save(userEntity);
    }
}
