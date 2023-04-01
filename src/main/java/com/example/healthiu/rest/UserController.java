package com.example.healthiu.rest;

import com.example.healthiu.entity.User;
import com.example.healthiu.entity.UserData;
import com.example.healthiu.security.jwt.JwtTokenProvider;
import com.example.healthiu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static org.springframework.http.ResponseEntity.ok;

@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @GetMapping("")
    public ResponseEntity<User> getUser(@RequestParam(name = "login") String userLogin, @RequestParam String token) {
        boolean validated = jwtTokenProvider.validateToken(token);
        User user = userService.findUserByLogin(userLogin);
        if (validated) {
            return ok(user);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
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
}
