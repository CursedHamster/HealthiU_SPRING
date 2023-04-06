package com.example.healthiu.service;

import com.example.healthiu.entity.BloodType;
import com.example.healthiu.entity.table.Test;
import com.example.healthiu.entity.TestData;
import com.example.healthiu.entity.table.User;

import java.util.Map;

public interface TestService {
    boolean checkIfTestExistsByUserLogin(String userLogin);

    double findBmi(TestData testData);

    String calculateResult(TestData testData);

    String calculateBadRation(BloodType bloodType, String testResult);

    String calculateGoodRation(BloodType bloodType, String testResult);

    Map<String, Double> calculateCalories(TestData testData);

    void saveTest(TestData testData, User user);

    Test findTestByLogin(String login);

    TestData castTestToTestData(Test test);
}
