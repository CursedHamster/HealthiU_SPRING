package com.example.healthiu.rest;

import com.example.healthiu.controller.TestController;
import com.example.healthiu.entity.Message;
import com.example.healthiu.entity.Test;
import com.example.healthiu.service.TestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestRestController {

    @Autowired
    private TestController testController;

    @Autowired
    private TestService testService;

    @GetMapping("/profile/tests/{login}")
    public String findUserTest(@PathVariable String login, Model model) throws Exception {
        if (!testService.checkIfTestExistsByUserLogin(login)) {
            model.addAttribute("isTestExists", false);
            throw new Exception("Test of " + login + " doesnt exist");
        }
        model.addAttribute("isTestExists", true);
        Test test = testService.findTestByLogin(login);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(test);
        testController.addAttributesForTest(model, test);
        return json;
    }
}
