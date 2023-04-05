package com.example.healthiu.entity.table;

import lombok.*;
import org.hibernate.Hibernate;

import jakarta.persistence.*;
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

    @Column
    private String goodProducts;

    @Column
    private String badProducts;

    @Column
    private String calories;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Test test = (Test) o;
        return userLogin != null && Objects.equals(userLogin, test.userLogin);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
