package com.example.healthiu.repository;

import com.example.healthiu.entity.table.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("testRepository")
public interface TestRepository extends JpaRepository<Test, Long> {
    Test findTestByUserLogin(String userLogin);
    List<Test> findAllByUserLogin(String userLogin);
    Optional<Test> findByUserLogin(String userLogin);
}
