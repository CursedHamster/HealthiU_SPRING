package com.example.healthiu.controller;

import com.example.healthiu.entity.BloodType;
import com.example.healthiu.entity.TestData;
import com.example.healthiu.entity.table.Test;
import com.example.healthiu.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/test")
public class TestController {
    private final TestService testService;

    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
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
            testService.saveTest(testData, login);
            return ok(testData);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/result/show")
    public ResponseEntity<TestData> getUserTestResult(@RequestParam String login) {
        if (testService.checkIfTestExistsByUserLogin(login)) {
            Test test = testService.findTestByLogin(login);
            TestData testData = testService.castTestToTestData(test);
            return ok(testData);
        }
        return ok(new TestData());
    }
}
