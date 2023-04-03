package com.example.healthiu.repository;

import com.example.healthiu.entity.table.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);

    User findUserByLogin(String login);

    User findUserByEmail(String email);
}
