package com.example.healthiu.controller;

import com.example.healthiu.entity.BloodType;
import com.example.healthiu.entity.TestData;
import com.example.healthiu.service.TestService;
import com.example.healthiu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/test")
public class TestController {
    private final TestService testService;
    private final UserService userService;

    @Autowired
    public TestController(TestService testService, UserService userService) {
        this.testService = testService;
        this.userService = userService;
    }

    @PostMapping("/result")
    public ResponseEntity<TestData> getTestResult(@RequestBody TestData testData) {
        double bmi = testService.findBmi(testData);
        String result = testService.calculateResult(testData);
        testData.setBmi(bmi);
        testData.setTestResult(result);
        String goodRation = testService.calculateGoodRation(BloodType.valueOf(testData.getBloodType()), result);
        String badRation = testService.calculateBadRation(BloodType.valueOf(testData.getBloodType()), result);
        Map<String, Double> calories = testService.calculateCalories(testData);
        testData.setGoodProducts(goodRation);
        testData.setBadProducts(badRation);
        testData.setCalories(calories);
        return ok(testData);
    }

    @PostMapping("/result/save")
    public ResponseEntity<TestData> saveTestResult(@RequestParam String login,
                                                   @RequestBody TestData testData) {
        if (testData != null) {
            try {
                testService.saveTest(testData, userService.findUserByLogin(login));
                return ok(testData);
            } catch (Exception e) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/result/show")
    public ResponseEntity<List<TestData>> getUserTestResult(@RequestParam String login) {
        List<TestData> testDataList = testService.findAllTestsByLogin(login);
        return ok(testDataList);
    }

    @DeleteMapping("/result/delete")
    public ResponseEntity<String> deleteTest(@RequestParam Long testId) {
        if (testService.checkIfTestExistsById(testId)) {
            testService.deleteTest(testId);
            return ok("success");
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
