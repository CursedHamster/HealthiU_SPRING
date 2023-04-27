package com.example.healthiu.service;

import com.example.healthiu.entity.BloodType;
import com.example.healthiu.entity.table.Test;
import com.example.healthiu.entity.TestData;
import com.example.healthiu.entity.table.User;

import java.util.List;
import java.util.Map;

public interface TestService {
    boolean checkIfTestExistsByUserLogin(String userLogin);

    List<TestData> findAllTestsByLogin(String login);

    double findBmi(TestData testData);

    String calculateResult(TestData testData);

    String calculateBadRation(BloodType bloodType, String testResult);

    String calculateGoodRation(BloodType bloodType, String testResult);

    Map<String, Double> calculateCalories(TestData testData);

    boolean checkIfTestExistsById(Long id);

    void saveTest(TestData testData, User user);

    void deleteTest(Long id);

    Test findTestByLogin(String login);
}
