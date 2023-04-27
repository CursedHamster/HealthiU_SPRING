package com.example.healthiu.entity;

import com.example.healthiu.entity.table.Test;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestData implements Serializable {
    private Long id;
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
    private String goodProducts = null;
    private String badProducts = null;
    private Map<String, Double> calories = null;
    private Timestamp timestamp;


    public TestData(Test test) {
        this.id = test.getId();
        this.gender = test.getGender();
        this.age = test.getAge();
        this.height = test.getHeight();
        this.weight = test.getWeight();
        this.chestSize = test.getChestSize();
        this.waistSize = test.getWaistSize();
        this.hipSize = test.getHipSize();
        this.bloodType = test.getBloodType();
        this.testResult = test.getTestResult();
        this.bmi = test.getBmi();
        this.goodProducts = test.getGoodProducts();
        this.badProducts = test.getBadProducts();
        Type empMapType = new TypeToken<Map<String, Double>>() {}.getType();
        this.calories = new Gson().fromJson(test.getCalories(), empMapType);
        this.timestamp = test.getTimestamp();
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
