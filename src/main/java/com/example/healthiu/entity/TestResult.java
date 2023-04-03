package com.example.healthiu.entity;

public enum TestResult {
    UNDERWEIGHT("underweight"),
    NORMAL("normal"),
    OVERWEIGHT("overweight"),
    OBESE("obese"),
    EXTREMELY_OBESE("extremelyObese"),
    MORBIDLY_OBESE("morbidlyObese"),

    RATION_O_STOP_GAIN("dietOStopGain"),
    RATION_O_STOP_LOSE("dietOStopLose"),
    RATION_O_LOSE("dietOLose"),
    RATION_O_GAIN("dietOGain"),

    RATION_A_STOP_GAIN("dietAStopGain"),
    RATION_A_STOP_LOSE("dietAStopLose"),
    RATION_A_LOSE("dietALose"),
    RATION_A_GAIN("dietAGain"),

    RATION_B_STOP_GAIN("dietBStopGain"),
    RATION_B_STOP_LOSE("dietBStopLose"),
    RATION_B_LOSE("dietBLose"),
    RATION_B_GAIN("dietBGain"),

    RATION_AB_STOP_GAIN("dietABStopGain"),
    RATION_AB_STOP_LOSE("dietABStopLose"),
    RATION_AB_LOSE("dietABLose"),
    RATION_AB_GAIN("dietABGain"),


    CALORIES_TO_MAINTAIN("maintain"),
    CALORIES_TO_LOSE_05("lose05"),
    CALORIES_TO_LOSE_1("lose1"),
    CALORIES_TO_GAIN_05("gain05"),
    CALORIES_TO_GAIN_1("gain1");
    private final String testResult;

    TestResult(String testResult) {
        this.testResult = testResult;
    }

    public String getTestResult() {
        return testResult;
    }
}
