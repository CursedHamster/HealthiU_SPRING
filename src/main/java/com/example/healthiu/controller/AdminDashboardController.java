package com.example.healthiu.controller;

import com.example.healthiu.entity.UserData;
import com.example.healthiu.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/admin-dashboard")
public class AdminDashboardController {
    private final UserService userService;

    public AdminDashboardController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserData>> getAllUsers() {
        return ok(userService.getAllUsers());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestParam String login) {
        if (userService.checkIfUserExists(login)) {
            userService.deleteUser(login);
            return ok("success");
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
