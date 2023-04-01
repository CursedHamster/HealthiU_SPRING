package com.example.healthiu.controller;

import com.example.healthiu.entity.*;
import com.example.healthiu.service.TestService;
import com.google.gson.Gson;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class TestController {
    @Autowired
    private TestService testService;

    @GetMapping("/")
    public String getTestHome(Model model) {
        initTest(model);
        return "test";
    }
    @GetMapping("/test")
    public String getTest(Model model) {
        initTest(model);
        return "test";
    }

    @PostMapping("/test/result")
    public String userRegistration(@Valid TestData testData, Model model) {
        double bmi = testService.findBmi(testData);
        String result = testService.calculateResult(testData);
        testData.setBmi(bmi);
        testData.setTestResult(result);
        model.addAttribute("bmi", String.format("%.1f", bmi));
        model.addAttribute("result", result);
        model.addAttribute("goodRation", testService.calculateGoodRation(BloodType.valueOf(testData.getBloodType()),
                result));
        model.addAttribute("badRation", testService.calculateBadRation(BloodType.valueOf(testData.getBloodType()),
                result));
        model.addAttribute("caloriesList", testService.calculateCalories(testData));
        model.addAttribute("testData", testData);

        return "test_result";
    }

    @GetMapping("/test/result")
    public String getResult() {
        return "redirect:/test";
    }

    @GetMapping("/test/result/save")
    public String saveResult(@RequestParam(name = "testData") String testDataJson,
                             HttpServletRequest req) {
        TestData testData = new Gson().fromJson(testDataJson, TestData.class);
        testService.saveTest(testData, req.getRemoteUser());
        return "test_result";
    }

    @GetMapping("/profile/test")
    public String showTest(Model model, HttpServletRequest req) {
        if (!testService.checkIfTestExistsByUserLogin(req.getRemoteUser())) {
            model.addAttribute("isTestInUser", false);
            return "show_test";
        } else {
            model.addAttribute("isTestInUser", true);
        }
        addAttributesForTest(model, testService.findTestByLogin(req.getRemoteUser()));
        return "show_test";
    }

    @GetMapping("/profile/tests")
    public String showUserTest(Model model) {
        model.addAttribute("isTestExists", false);
        return "show_user_test";
    }

    private void initTest(Model model) {
        TestData testData = new TestData();
        testData.setGender(Gender.MALE.getGender());
        model.addAttribute("testData", testData);
        model.addAttribute("allGenders", Stream.of(Gender.values())
                .map(Gender::getGender)
                .collect(Collectors.toList()));
        model.addAttribute("allBloodTypes", BloodType.values());
    }

    public void addAttributesForTest(Model model, Test test) {
        model.addAttribute("gender", test.getGender());
        model.addAttribute("age", test.getAge());
        model.addAttribute("height", test.getHeight());
        model.addAttribute("weight", test.getWeight());
        model.addAttribute("chestSize", test.getChestSize());
        model.addAttribute("waistSize", test.getWaistSize());
        model.addAttribute("hipSize", test.getHipSize());
        model.addAttribute("bloodType", test.getBloodType());
        model.addAttribute("testResult", test.getTestResult());
        model.addAttribute("bmi", String.format("%.1f", test.getBmi()));
    }
}
