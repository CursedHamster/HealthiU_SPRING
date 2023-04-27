package com.example.healthiu.entity.table;

import com.example.healthiu.entity.TestData;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Timestamp;
import java.util.Date;
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
    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
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

    @Column
    private Timestamp timestamp;

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

    public Test(User user, TestData testData, String calories) {
        this.user = user;
        this.gender = testData.getGender();
        this.age = testData.getAge();
        this.height = testData.getHeight();
        this.weight = testData.getWeight();
        this.chestSize = testData.getChestSize();
        this.waistSize = testData.getWaistSize();
        this.hipSize = testData.getHipSize();
        this.bloodType = testData.getBloodType();
        this.testResult = testData.getTestResult();
        this.bmi = testData.getBmi();
        this.goodProducts = testData.getGoodProducts();
        this.badProducts = testData.getBadProducts();
        this.calories = calories;
        this.timestamp = new Timestamp(new Date().getTime());
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
