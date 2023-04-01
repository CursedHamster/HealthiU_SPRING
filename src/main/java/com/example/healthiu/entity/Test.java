package com.example.healthiu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "test")
@NoArgsConstructor
@AllArgsConstructor
public class Test {
    @Id
    private String userLogin;

    @Column
    private String gender;

    @Column
    private int age;

    @Column
    private double height;

    @Column
    private double weight;

    @Column
    private double chestSize;
    @Column
    private double waistSize;

    @Column
    private double hipSize;

    @Column
    private String bloodType;

    @Column
    private String testResult;

    @Column
    private double bmi;
}
