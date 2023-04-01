package com.example.healthiu.entity;

import com.google.gson.Gson;

import java.io.Serializable;

public class TestData implements Serializable {
    private String gender;
    private int age;
    private double height;
    private double weight;
    private double chestSize;
    private double waistSize;
    private double hipSize;
    private String bloodType;
    private String testResult;
    private double bmi;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getChestSize() {
        return chestSize;
    }

    public void setChestSize(double chestSize) {
        this.chestSize = chestSize;
    }
    public double getWaistSize() {
        return waistSize;
    }
    public void setWaistSize(double waistSize) {
        this.waistSize = waistSize;
    }

    public double getHipSize() {
        return hipSize;
    }

    public void setHipSize(double hipSize) {
        this.hipSize = hipSize;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getTestResult() {
        return testResult;
    }

    public void setTestResult(String testResult) {
        this.testResult = testResult;
    }

    public double getBmi() {
        return bmi;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    public TestData() {
    }

    public TestData(String gender, int age, double height, double weight, double chestSize, double waistSize,
                    double hipSize, String bloodType) {
        this.gender = gender;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.chestSize = chestSize;
        this.waistSize = waistSize;
        this.hipSize = hipSize;
        this.bloodType = bloodType;
    }

    public TestData(String gender, int age, double height, double weight, double chestSize, double waistSize,
                    double hipSize, String bloodType, String testResult, double bmi) {
        this.gender = gender;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.chestSize = chestSize;
        this.waistSize = waistSize;
        this.hipSize = hipSize;
        this.bloodType = bloodType;
        this.testResult = testResult;
        this.bmi = bmi;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
