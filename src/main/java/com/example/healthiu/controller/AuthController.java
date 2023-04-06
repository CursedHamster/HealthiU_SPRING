package com.example.healthiu.controller;

import com.example.healthiu.entity.AuthenticationRequest;
import com.example.healthiu.entity.table.RefreshTokenRequest;
import com.example.healthiu.entity.UserData;
import com.example.healthiu.entity.table.User;
import com.example.healthiu.entity.table.VerificationToken;
import com.example.healthiu.security.JwtTokenProvider;
import com.example.healthiu.security.verification.OnRegistrationCompleteEvent;
import com.example.healthiu.service.RefreshTokenService;
import com.example.healthiu.service.UserService;
import com.example.healthiu.service.VerificationTokenService;
import com.fasterxml.uuid.Generators;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private static final String VERIFICATION_URL = "http://localhost:5173/sign-up/verification";
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final RefreshTokenService refreshTokenService;
    private final ApplicationEventPublisher eventPublisher;
    private final VerificationTokenService verificationTokenService;

    @Autowired
    public AuthController(JwtTokenProvider jwtTokenProvider, UserService userService,
                          RefreshTokenService refreshTokenService, ApplicationEventPublisher eventPublisher,
                          VerificationTokenService verificationTokenService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;
        this.eventPublisher = eventPublisher;
        this.verificationTokenService = verificationTokenService;
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
        if (userService.checkUserLoginAndPassword(data.getUsername(), data.getPassword())
                && userService.checkIfEnabled(data.getUsername())) {
            return ok(loginUser(data.getUsername()));
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody UserData userData, HttpServletRequest request) {
        String username = userData.getLogin();
        if (userService.checkIfUserExist(username) || userService.checkIfEmailExists(userData.getEmail())
                && userService.checkIfEnabled(username)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        User user = userService.register(userData);
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(request.getLocale(),
                VERIFICATION_URL, user));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/admin-register")
    public ResponseEntity<Map<String, Object>> register(@RequestBody UserData userData, @RequestParam(name = "role") String role,
                                                        HttpServletRequest request) {
        String username = userData.getLogin();
        if (userService.checkIfUserExist(username) || userService.checkIfEmailExists(userData.getEmail())
                && userService.checkIfEnabled(username)) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        User user = userService.register(userData, role);
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(request.getLocale(),
                VERIFICATION_URL, user));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/verify")
    public ResponseEntity<Map<String, Object>> verify(@RequestParam(name = "token") String token) {
        VerificationToken verificationToken = verificationTokenService.getVerificationToken(token);
        if (verificationToken == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        user.setEnabled(true);
        verificationTokenService.deleteVerificationTokenById(verificationToken.getId());
        userService.saveUser(user);
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
