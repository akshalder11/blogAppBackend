package com.blogapp.blogappbackend.repository;

import com.blogapp.blogappbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // Find user by email for login
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

    // Check if email already exists
    boolean existsByEmail(String email);

    // Check if username already exists
    boolean existsByUsername(String username);
}
