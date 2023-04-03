package com.example.healthiu.entity;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
    private String goodProducts = null;
    private String badProducts = null;
    private Map<String, Double> calories = null;
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
