package com.example.healthiu.repository;

import com.example.healthiu.entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("testRepository")
public interface TestRepository extends JpaRepository<Test, String> {
    Test findTestByUserLogin(String userLogin);
}
