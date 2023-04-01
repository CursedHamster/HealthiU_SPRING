package com.example.healthiu.service;

import com.example.healthiu.entity.BloodType;
import com.example.healthiu.entity.Test;
import com.example.healthiu.entity.TestData;

import java.util.List;

public interface TestService {
    boolean checkIfTestExistsByUserLogin(String userLogin);

    double findBmi(TestData testData);

    String calculateResult(TestData testData);

    String calculateBadRation(BloodType bloodType, String testResult);

    String calculateGoodRation(BloodType bloodType, String testResult);

    List<String> calculateCalories(TestData testData);

    void saveTest(TestData testData, String userLogin);

    Test findTestByLogin(String login);
}
