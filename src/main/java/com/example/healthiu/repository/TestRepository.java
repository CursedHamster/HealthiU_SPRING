package com.example.healthiu.repository;

import com.example.healthiu.entity.table.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("testRepository")
public interface TestRepository extends JpaRepository<Test, String> {
    Test findTestByUserLogin(String userLogin);
}
