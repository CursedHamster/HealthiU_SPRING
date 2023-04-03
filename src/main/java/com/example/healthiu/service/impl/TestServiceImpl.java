package com.example.healthiu.service.impl;

import com.example.healthiu.entity.*;
import com.example.healthiu.entity.table.Test;
import com.example.healthiu.repository.TestRepository;
import com.example.healthiu.service.TestService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

@Service("testService")
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;

    @Autowired
    public TestServiceImpl(TestRepository testRepository) {
        this.testRepository = testRepository;
    }

    @Override
    public boolean checkIfTestExistsByUserLogin(String userLogin) {
        return testRepository.findById(userLogin).isPresent();
    }

    @Override
    public double findBmi(TestData testData) {
        return testData.getWeight() / Math.pow(testData.getHeight() / 100, 2);
    }

    @Override
    public String calculateResult(TestData testData) {
        String testResult = TestResult.NORMAL.getTestResult();
        int bmiCo = calculateBmi(testData);
        int waistToHipRatioCo = calculateWaistToHipRatio(testData);
        int waistToHeightRatioCo = calculateWaistToHeightRatio(testData);
        int waistRatioCo = calculateWaistRatio(testData);
        int fatRatioCo = calculateBodyFatRatio(testData);
        int chestToWaistRatioCo = calculateChestToWaistRatio(testData);
        int sumCo = bmiCo + waistToHipRatioCo + waistToHeightRatioCo + waistRatioCo + fatRatioCo + chestToWaistRatioCo;

        if (testData.getGender().equals(Gender.MALE.getGender())) {
            if (waistRatioCo >= 1) {
                if (chestToWaistRatioCo == 1) {
                    if (bmiCo <= 2) {
                        testResult = TestResult.OBESE.getTestResult();
                    } else if (bmiCo == 3) {
                        testResult = TestResult.EXTREMELY_OBESE.getTestResult();
                    } else if (bmiCo == 4) {
                        testResult = TestResult.MORBIDLY_OBESE.getTestResult();
                    }
                } else {
                    if (waistToHeightRatioCo <= 0) {
                        testResult = TestResult.NORMAL.getTestResult();
                    } else {
                        testResult = TestResult.OVERWEIGHT.getTestResult();
                    }
                }
            } else {
                if (waistToHeightRatioCo <= 0) {
                    if (sumCo <= -3) {
                        testResult = TestResult.UNDERWEIGHT.getTestResult();
                    } else {
                        testResult = TestResult.NORMAL.getTestResult();
                    }
                } else {
                    testResult = TestResult.OVERWEIGHT.getTestResult();
                }
            }
        } else {
            if (waistRatioCo >= 1) {
                if (waistToHipRatioCo == 0) {
                    if (chestToWaistRatioCo == 1) {
                        if (bmiCo <= 1) {
                            testResult = TestResult.OVERWEIGHT.getTestResult();
                        } else if (bmiCo == 2) {
                            testResult = TestResult.OBESE.getTestResult();
                        } else {
                            testResult = TestResult.EXTREMELY_OBESE.getTestResult();
                        }
                    } else {
                        if (bmiCo <= 0) {
                            testResult = TestResult.NORMAL.getTestResult();
                        } else if (bmiCo == 1) {
                            testResult = TestResult.OVERWEIGHT.getTestResult();
                        } else if (bmiCo == 2) {
                            testResult = TestResult.OBESE.getTestResult();
                        } else {
                            testResult = TestResult.EXTREMELY_OBESE.getTestResult();
                        }
                    }
                } else {
                    testResult = switch (bmiCo) {
                        case 2 -> TestResult.OBESE.getTestResult();
                        case 3 -> TestResult.EXTREMELY_OBESE.getTestResult();
                        case 4 -> TestResult.MORBIDLY_OBESE.getTestResult();
                        default -> TestResult.OVERWEIGHT.getTestResult();
                    };
                }
            } else {
                if (waistToHeightRatioCo <= 0) {
                    if (bmiCo < 0) {
                        testResult = TestResult.UNDERWEIGHT.getTestResult();
                    } else if (bmiCo < 1) {
                        testResult = TestResult.NORMAL.getTestResult();
                    } else {
                        testResult = TestResult.OVERWEIGHT.getTestResult();
                    }
                } else {
                    testResult = TestResult.OVERWEIGHT.getTestResult();
                }
            }
        }


        return testResult;
    }

    @Override
    public String calculateGoodRation(BloodType bloodType, String testResult) {
        String ration;
        switch (bloodType) {
            case A -> {
                if (testResult.equals(TestResult.UNDERWEIGHT.getTestResult())) {
                    ration = TestResult.RATION_A_GAIN.getTestResult();
                } else {
                    ration = TestResult.RATION_A_LOSE.getTestResult();
                }
            }
            case B -> {
                if (testResult.equals(TestResult.UNDERWEIGHT.getTestResult())) {
                    ration = TestResult.RATION_B_GAIN.getTestResult();
                } else {
                    ration = TestResult.RATION_B_LOSE.getTestResult();
                }
            }
            case AB -> {
                if (testResult.equals(TestResult.UNDERWEIGHT.getTestResult())) {
                    ration = TestResult.RATION_AB_GAIN.getTestResult();
                } else {
                    ration = TestResult.RATION_AB_LOSE.getTestResult();
                }
            }
            default -> {
                if (testResult.equals(TestResult.UNDERWEIGHT.getTestResult())) {
                    ration = TestResult.RATION_O_GAIN.getTestResult();
                } else {
                    ration = TestResult.RATION_O_LOSE.getTestResult();
                }
            }
        }
        return ration;
    }

    @Override
    public String calculateBadRation(BloodType bloodType, String testResult) {
        String ration;
        switch (bloodType) {
            case A -> {
                if (testResult.equals(TestResult.UNDERWEIGHT.getTestResult())) {
                    ration = TestResult.RATION_A_STOP_LOSE.getTestResult();
                } else {
                    ration = TestResult.RATION_A_STOP_GAIN.getTestResult();
                }
            }
            case B -> {
                if (testResult.equals(TestResult.UNDERWEIGHT.getTestResult())) {
                    ration = TestResult.RATION_B_STOP_LOSE.getTestResult();
                } else {
                    ration = TestResult.RATION_B_STOP_GAIN.getTestResult();
                }
            }
            case AB -> {
                if (testResult.equals(TestResult.UNDERWEIGHT.getTestResult())) {
                    ration = TestResult.RATION_AB_STOP_LOSE.getTestResult();
                } else {
                    ration = TestResult.RATION_AB_STOP_GAIN.getTestResult();
                }
            }
            default -> {
                if (testResult.equals(TestResult.UNDERWEIGHT.getTestResult())) {
                    ration = TestResult.RATION_O_STOP_LOSE.getTestResult();
                } else {
                    ration = TestResult.RATION_O_STOP_GAIN.getTestResult();
                }
            }
        }
        return ration;
    }

    @Override
    public Map<String, Double> calculateCalories(TestData testData) {
        Map<String, Double> calories = new HashMap<>();
        double kgInCal = 7000;
        double baseCalorieCalculation = 10 * testData.getWeight() + 6.25 * testData.getHeight() - 5 * testData.getAge();
        double caloriesToMaintain = (testData.getGender().equals(Gender.MALE.getGender())) ?
                (baseCalorieCalculation + 5) * 1.5 : (baseCalorieCalculation - 161) * 1.2;
        double caloriesToLose05Kg = caloriesToMaintain - kgInCal * 0.5 / 7;
        double caloriesToLose1Kg = caloriesToMaintain - kgInCal / 7;
        double caloriesToGain05Kg = caloriesToMaintain + kgInCal * 0.5 / 7;
        double caloriesToGain1Kg = caloriesToMaintain + kgInCal / 7;

        if (testData.getTestResult().equals(TestResult.UNDERWEIGHT.getTestResult())) {
            calories.put(TestResult.CALORIES_TO_MAINTAIN.getTestResult(), caloriesToMaintain);
            calories.put(TestResult.CALORIES_TO_GAIN_05.getTestResult(), caloriesToGain05Kg);
            calories.put(TestResult.CALORIES_TO_GAIN_1.getTestResult(), caloriesToGain1Kg);
        } else if (testData.getTestResult().equals(TestResult.NORMAL.getTestResult())) {
            calories.put(TestResult.CALORIES_TO_MAINTAIN.getTestResult(), caloriesToMaintain);
        } else if (testData.getTestResult().equals(TestResult.OVERWEIGHT.getTestResult())) {
            calories.put(TestResult.CALORIES_TO_MAINTAIN.getTestResult(), caloriesToMaintain);
            calories.put(TestResult.CALORIES_TO_LOSE_05.getTestResult(), caloriesToLose05Kg);
        } else {
            calories.put(TestResult.CALORIES_TO_MAINTAIN.getTestResult(), caloriesToMaintain);
            calories.put(TestResult.CALORIES_TO_LOSE_05.getTestResult(), caloriesToLose05Kg);
            calories.put(TestResult.CALORIES_TO_LOSE_1.getTestResult(), caloriesToLose1Kg);
        }

        return calories;
    }

    @Override
    public void saveTest(TestData testData, String userLogin) {
        try {
            String caloriesString = new Gson().toJson(testData.getCalories());
            Test test = new Test(userLogin,
                    testData.getGender(),
                    testData.getAge(),
                    testData.getHeight(),
                    testData.getWeight(),
                    testData.getChestSize(),
                    testData.getWaistSize(),
                    testData.getHipSize(),
                    testData.getBloodType(),
                    testData.getTestResult(),
                    testData.getBmi(),
                    testData.getGoodProducts(),
                    testData.getBadProducts(),
                    caloriesString);
            testRepository.save(test);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public Test findTestByLogin(String login) {
        return testRepository.findTestByUserLogin(login);
    }

    @Override
    public TestData castTestToTestData(Test test) {
        Type empMapType = new TypeToken<Map<String, Double>>() {
        }.getType();
        Map<String, Double> caloriesMap = new Gson().fromJson(test.getCalories(), empMapType);
        return new TestData(
                test.getGender(),
                test.getAge(),
                test.getHeight(),
                test.getWeight(),
                test.getChestSize(),
                test.getWaistSize(),
                test.getHipSize(),
                test.getBloodType(),
                test.getTestResult(),
                test.getBmi(),
                test.getGoodProducts(),
                test.getBadProducts(),
                caloriesMap
        );
    }


    /////////////////// CALCULATIONS
    private int calculateBmi(TestData testData) {
        int obesity = 0;

        double bmi = findBmi(testData);
        double minBmi = bmiForAge(testData.getAge(), 19);
        double maxBmi = bmiForAge(testData.getAge(), 24);
        double bmiDifference = 5;

        if (bmi < minBmi) {
            obesity = -1;
        }

        if (bmi >= maxBmi && bmi < maxBmi + bmiDifference) {
            obesity = 1;
        }

        if (bmi >= maxBmi + bmiDifference && bmi < maxBmi + bmiDifference * 2) {
            obesity = 2;
        }

        if (bmi >= maxBmi + bmiDifference * 2 && bmi < maxBmi + bmiDifference * 3) {
            obesity = 3;
        }

        if (bmi >= maxBmi + bmiDifference * 3) {
            obesity = 4;
        }

        return obesity;
    }

    private int calculateWaistToHipRatio(TestData testData) {
        double ratio = testData.getWaistSize() / testData.getHipSize();
        int obesity = 0;
        double maleBias = 0.9;
        double femaleBias = 0.8;
        double difference = 0.05;

        if (testData.getGender().equals(Gender.MALE.getGender())) {
            obesity = getObesity(ratio, obesity, maleBias, difference);
        } else {
            obesity = getObesity(ratio, obesity, femaleBias, difference);
        }
        return obesity;
    }

    private int calculateWaistToHeightRatio(TestData testData) {
        double ratio = testData.getWaistSize() / testData.getHeight();
        int obesity = 0;

        if (testData.getAge() <= 15) {
            if (ratio < 0.34) {
                obesity = -2;
            }
            if (ratio >= 0.34 && ratio < 0.46) {
                obesity = -1;
            }
            if (ratio >= 0.52 && ratio < 0.64) {
                obesity = 1;
            }
            if (ratio >= 0.64) {
                obesity = 2;
            }
        } else {
            if (testData.getGender().equals(Gender.MALE.getGender())) {
                if (ratio < 0.35) {
                    obesity = -2;
                }
                if (ratio >= 0.35 && ratio < 0.43) {
                    obesity = -1;
                }
                if (ratio >= 0.53 && ratio < 0.58) {
                    obesity = 1;
                }
                if (ratio >= 0.58 && ratio < 0.63) {
                    obesity = 2;
                }
                if (ratio >= 0.63) {
                    obesity = 3;
                }
            } else {
                if (ratio < 0.35) {
                    obesity = -2;
                }
                if (ratio >= 0.35 && ratio < 0.42) {
                    obesity = -1;
                }
                if (ratio >= 0.49 && ratio < 0.54) {
                    obesity = 1;
                }
                if (ratio >= 0.54 && ratio < 0.58) {
                    obesity = 2;
                }
                if (ratio >= 0.58) {
                    obesity = 3;
                }
            }
        }
        return obesity;
    }

    private int calculateWaistRatio(TestData testData) {
        int obesity = 0;
        double waistSize = testData.getWaistSize();

        if (testData.getGender().equals(Gender.MALE.getGender())) {
            if (waistSize > 94 && waistSize <= 100) {
                obesity = 1;
            }
            if (waistSize > 100) {
                obesity = 2;
            }
        } else {
            if (waistSize > 80 && waistSize <= 88) {
                obesity = 1;
            }
            if (waistSize > 88) {
                obesity = 2;
            }
        }

        return obesity;
    }

    private int calculateBodyFatRatio(TestData testData) {
        int obesity = 0;
        double hipBias = 76;
        double heightBias = 188;
        double fatBias = 4;

        double hipSize = testData.getHipSize();
        double height = testData.getHeight();
        double hipDifference = (hipSize >= hipBias) ? hipSize - hipBias : hipBias - hipSize;
        double heightDifference = (height <= heightBias) ? heightBias - height : height - heightBias;
        double fat = fatBias + (hipDifference + heightDifference) / 2;

        if (testData.getGender().equals(Gender.MALE.getGender())) {
            if (fat < 5) {
                obesity = -2;
            }
            if (fat >= 5 && fat < 8) {
                obesity = -1;
            }
            if (fat >= 19 && fat < 25) {
                obesity = 1;
            }
            if (fat >= 25) {
                obesity = 2;
            }
        } else {
            if (fat < 18) {
                obesity = -2;
            }
            if (fat >= 18 && fat < 21) {
                obesity = -1;
            }
            if (fat >= 33 && fat < 39) {
                obesity = 1;
            }
            if (fat >= 39) {
                obesity = 2;
            }
        }
        return obesity;
    }

    private int calculateChestToWaistRatio(TestData testData) {
        int obesity = 0;
        if (testData.getChestSize() <= testData.getWaistSize()) {
            obesity = 3;
        }
        return obesity;
    }
    /////////////////// CALCULATIONS


    //////// CALCULATION HELPERS
    private double bmiForAge(int age, double bmi) {
        for (int i = 14; i < 130; i++) {
            if (i == 25) {
                bmi++;
            }
            if (i == 35) {
                bmi++;
            }
            if (i == 45) {
                bmi++;
            }

            if (i == 55) {
                bmi++;
            }

            if (i == 65) {
                bmi++;
            }
            if (age == i) {
                return bmi;
            }
        }
        return bmi;
    }

    private int getObesity(double ratio, int obesity, double maleBias, double difference) {
        if (ratio > maleBias && ratio <= maleBias + difference) {
            obesity = 1;
        }
        if (ratio > maleBias + difference && ratio <= maleBias + difference * 2) {
            obesity = 2;
        }
        if (ratio > maleBias + difference * 2) {
            obesity = 3;
        }
        return obesity;
    }
}
