package com.example.healthiu.entity.table;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@Table(name = "test")
@NoArgsConstructor
@AllArgsConstructor
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "login")
    private User user;

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

    @Column
    private String goodProducts;

    @Column
    private String badProducts;

    @Column
    private String calories;

    public Test(User user, String gender, int age, double height, double weight, double chestSize, double waistSize, double hipSize, String bloodType) {
        this.user = user;
        this.gender = gender;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.chestSize = chestSize;
        this.waistSize = waistSize;
        this.hipSize = hipSize;
        this.bloodType = bloodType;
    }

    public Test(User user, String gender, int age, double height, double weight, double chestSize, double waistSize, double hipSize, String bloodType, String testResult, double bmi, String goodProducts, String badProducts, String calories) {
        this.user = user;
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
        this.goodProducts = goodProducts;
        this.badProducts = badProducts;
        this.calories = calories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Test test = (Test) o;
        return id != null && Objects.equals(id, test.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
