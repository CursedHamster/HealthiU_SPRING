package com.example.healthiu.controller;

import com.example.healthiu.entity.User;
import com.example.healthiu.entity.UserData;
import com.example.healthiu.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Controller
public class ProfileController {
    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public String getEditProfile(Model model, HttpServletRequest req) {
        addAttributesForUser(model, userService.findUserByLogin(req.getRemoteUser()));
        return "profile";
    }

    @PostMapping("/profile/edit")
    public String editProfile(UserData userData, Model model) {
        if (userService.checkIfEmailExists(userData.getEmail()) &&
                !Objects.equals(userService.findUserByEmail(userData.getEmail()).getLogin(), userData.getLogin())) {
            model.addAttribute("userData", userData);
            model.addAttribute("emailExists", "Користувач з цією поштою вже існує.");
            return "profile";
        }
        if (userService.checkChangesCount(userData) == 0) {
            model.addAttribute("successfulEdit", "Ніяких змін не було зроблено!");
            return "profile";
        }
        userService.updateUserInfo(userData);
        model.addAttribute("successfulEdit", "Профіль успішно змінений!");
        return "profile";
    }

    private void addAttributesForUser(Model model, User user) {
        UserData userData = new UserData();
        BeanUtils.copyProperties(user, userData);
        userData.setConfirmPassword(userData.getPassword());
        model.addAttribute("userData", userData);
        model.addAttribute("login", userData.getLogin());
        model.addAttribute("name", userData.getName());
        model.addAttribute("email", userData.getEmail());
        model.addAttribute("dateOfBirth", user.getDateOfBirth());
        model.addAttribute("role", user.getRole());
        model.addAttribute("userExists", null);
        model.addAttribute("successfulEdit", null);
    }
}
