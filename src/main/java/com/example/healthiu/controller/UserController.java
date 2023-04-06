package com.example.healthiu.controller;

import com.example.healthiu.entity.UserData;
import com.example.healthiu.entity.table.User;
import com.example.healthiu.security.JwtTokenProvider;
import com.example.healthiu.service.UserService;
import com.example.healthiu.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/user")
public class UserController {
    UserService userService;

    JwtTokenProvider jwtTokenProvider;

    StorageService storageService;

    @Autowired
    public UserController(UserService userService, JwtTokenProvider jwtTokenProvider, StorageService storageService) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.storageService = storageService;
    }

    @GetMapping
    public ResponseEntity<User> getUser(@RequestParam(name = "login") String userLogin, @RequestParam String token) {
        boolean validated = jwtTokenProvider.validateToken(token);
        User user = userService.findUserByLogin(userLogin);
        if (validated) {
            return ok(user);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/{login}")
    public ResponseEntity<User> getUserInfo(@PathVariable(name = "login") String login) {
        if (userService.checkIfUserExist(login)) {
            User user = userService.findUserByLogin(login);
            user.setPassword(null);
            user.setEmail(null);
            return ok(user);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/change")
    public ResponseEntity<User> changeUser(@RequestBody UserData userData) {
        if (userService.checkIfEmailExists(userData.getEmail()) &&
                !Objects.equals(userService.findUserByEmail(userData.getEmail()).getLogin(), userData.getLogin())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        if (userService.checkChangesCount(userData) == 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (userData.getPassword().length() > 0 && !Objects.equals(userData.getPassword(), userData.getConfirmPassword())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        userService.updateUserInfo(userData);
        User user = userService.findUserByLogin(userData.getLogin());
        return ok(user);
    }

    @PostMapping(value = "/change-picture", consumes = "multipart/form-data")
    public String changeProfilePicture(@RequestParam(name = "file") MultipartFile file) {
        System.out.println("started meow");
        try {
            storageService.initializeFirebase();
            System.out.println(storageService.uploadFile(file));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "meow";
    }
}
