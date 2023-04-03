package com.example.healthiu.controller;

import com.example.healthiu.entity.AuthenticationRequest;
import com.example.healthiu.entity.RefreshTokenRequest;
import com.example.healthiu.entity.UserData;
import com.example.healthiu.security.JwtTokenProvider;
import com.example.healthiu.service.RefreshTokenService;
import com.example.healthiu.service.UserService;
import com.fasterxml.uuid.Generators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    JwtTokenProvider jwtTokenProvider;
    UserService userService;
    RefreshTokenService refreshTokenService;

    @Autowired
    public AuthController(JwtTokenProvider jwtTokenProvider, UserService userService, RefreshTokenService refreshTokenService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/renew")
    public ResponseEntity<Map<String, Object>> renew(@RequestBody RefreshTokenRequest data) {
        Map<String, Object> model = new HashMap<>();
        String username = data.getUsername();
        String refreshToken = data.getRefreshToken();
        if (!refreshTokenService.checkIfRefreshTokenExists(username)) {
            return ok(null);
        }
        if (refreshTokenService.checkIfRefreshTokenIsRight(username, refreshToken)) {
            String token = jwtTokenProvider.createToken(username, userService.findUserByLogin(username).getRole());
            model.put("token", token);
            return ok(model);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody AuthenticationRequest data) {
        if (userService.checkUserLoginAndPassword(data.getUsername(), data.getPassword())) {
            return ok(loginUser(data.getUsername()));
        }
        System.out.println("bad credentials");
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody UserData userData) {
        String username = userData.getLogin();
        if (userService.checkIfUserExist(username) || userService.checkIfEmailExists(userData.getEmail())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        userService.register(userData);
        return ok(loginUser(username));
    }

    @PostMapping("/admin-register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody UserData userData, @RequestParam(name = "role") String role) {
        String username = userData.getLogin();
        if (userService.checkIfUserExist(username) || userService.checkIfEmailExists(userData.getEmail())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        userService.register(userData, role);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public Map<String, Object> loginUser(String username) {
        String token = jwtTokenProvider.createToken(username, userService.findUserByLogin(username).getRole());
        UUID refreshToken = Generators.randomBasedGenerator().generate();
        refreshTokenService.addNewRefreshToken(refreshToken.toString(), username);
        Map<String, Object> model = new HashMap<>();
        model.put("username", username);
        model.put("token", token);
        model.put("refreshToken", refreshToken);

        return model;
    }
}
