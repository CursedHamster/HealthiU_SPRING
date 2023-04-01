package com.example.healthiu.rest;

import com.example.healthiu.entity.UserData;
import com.example.healthiu.repository.UserRepository;
import com.example.healthiu.entity.AuthenticationRequest;
import com.example.healthiu.security.jwt.JwtTokenProvider;
import com.example.healthiu.entity.RefreshTokenRequest;
import com.example.healthiu.service.RefreshTokenService;
import com.example.healthiu.service.UserService;
import com.fasterxml.uuid.Generators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserService userService;

    @Autowired
    RefreshTokenService refreshTokenService;

    @PostMapping("/renew")
    public ResponseEntity<Map<String, Object>> renew(@RequestBody RefreshTokenRequest data) {
        Map<String, Object> model = new HashMap<>();

        String username = data.getUsername();
        String refreshToken = data.getRefreshToken();

        if (!refreshTokenService.checkIfRefreshTokenExists(username)) {
            return ok(null);
        }

        if (refreshTokenService.checkIfRefreshTokenIsRight(username, refreshToken)){
            String token = jwtTokenProvider.createToken(username, userService.findUserByLogin(username).getRole());
            model.put("token", token);
            return ok(model);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody AuthenticationRequest data) {
        try {
            return ok(loginUser(data.getUsername(), data.getPassword()));
        } catch (AuthenticationException e) {
            System.out.println("bad credentials");
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
//            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody UserData userData) {
        String username = userData.getLogin();
        if (userService.checkIfUserExist(username) || userService.checkIfEmailExists(userData.getEmail())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        userService.register(userData);

        return ok(loginUser(username, userData.getPassword()));
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

    public Map<String, Object> loginUser(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        String token = jwtTokenProvider.createToken(username, userService.findUserByLogin(username).getRole());

        // generate a refreshToken
        UUID refreshToken = Generators.randomBasedGenerator().generate();

        refreshTokenService.addNewRefreshToken(refreshToken.toString(), username);

        Map<String, Object> model = new HashMap<>();
        model.put("username", username);
        model.put("token", token);
        model.put("refreshToken", refreshToken);

        return model;
    }
}
