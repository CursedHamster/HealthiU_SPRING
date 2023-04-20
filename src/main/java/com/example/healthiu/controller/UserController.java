package com.example.healthiu.controller;

import com.example.healthiu.entity.UserData;
import com.example.healthiu.entity.table.User;
import com.example.healthiu.entity.table.VerificationToken;
import com.example.healthiu.security.JwtTokenProvider;
import com.example.healthiu.security.verification.VerificationEvent;
import com.example.healthiu.service.StorageService;
import com.example.healthiu.service.UserService;
import com.example.healthiu.service.VerificationTokenService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Calendar;
import java.util.Map;
import java.util.Objects;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private static final String VERIFICATION_URL = "https://healthiu.netlify.app/profile/verification";
    private final UserService userService;

    private final JwtTokenProvider jwtTokenProvider;

    private final StorageService storageService;
    private final ApplicationEventPublisher eventPublisher;
    private final VerificationTokenService verificationTokenService;

    @Autowired
    public UserController(UserService userService, JwtTokenProvider jwtTokenProvider,
                          StorageService storageService, ApplicationEventPublisher eventPublisher,
                          VerificationTokenService verificationTokenService) {
        this.userService = userService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.storageService = storageService;
        this.eventPublisher = eventPublisher;
        this.verificationTokenService = verificationTokenService;
    }

    @GetMapping
    public ResponseEntity<UserData> getUser(@RequestParam(name = "login") String userLogin, @RequestParam String token) {
        boolean validated = jwtTokenProvider.validateToken(token);
        User user = userService.findUserByLogin(userLogin);
        if (validated) {
            return ok(new UserData(user));
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("/{login}")
    public ResponseEntity<UserData> getUserInfo(@PathVariable(name = "login") String login) {
        if (userService.checkIfUserExists(login)) {
            User user = userService.findUserByLogin(login);
            user.setEmail(null);
            return ok(new UserData(user));
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/change")
    public ResponseEntity<UserData> changeUser(@RequestBody UserData userData, HttpServletRequest request) {
        if (userService.checkIfEmailExists(userData.getEmail()) &&
                !Objects.equals(userService.findUserByEmail(userData.getEmail()).getLogin(), userData.getLogin())) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        boolean emailChanged = userService.checkEmailChange(userData);
        if (userService.checkChangesCount(userData) == 0 && !emailChanged) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } else if (userData.getPassword().length() > 0 && !Objects.equals(userData.getPassword(), userData.getConfirmPassword())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        userService.updateUserInfo(userData);
        User user = userService.findUserByLogin(userData.getLogin());
        if (emailChanged) {
            eventPublisher.publishEvent(new VerificationEvent(request.getLocale(),
                    VERIFICATION_URL, user, userData.getEmail()));
            return new ResponseEntity<>(new UserData(user), HttpStatus.ACCEPTED);
        }
        return ok(new UserData(user));
    }

    @PutMapping(value = "/change-picture", consumes = "multipart/form-data")
    public ResponseEntity<String> changeProfilePicture(@RequestParam(name = "file") MultipartFile file) {
        try {
            return ok(storageService.uploadFile(file));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/verify")
    public ResponseEntity<Map<String, Object>> verify(@RequestParam(name = "token") String token) {
        VerificationToken verificationToken = verificationTokenService.getVerificationToken(token);
        if (verificationToken == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User user = verificationToken.getUser();
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        user.setEmail(verificationToken.getEmail());
        verificationTokenService.deleteVerificationTokenById(verificationToken.getId());
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
