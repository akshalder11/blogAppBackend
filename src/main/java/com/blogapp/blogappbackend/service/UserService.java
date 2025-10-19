package com.blogapp.blogappbackend.service;

import com.blogapp.blogappbackend.entity.User;

import java.util.Optional;

public interface UserService {

    User registerUser(User user) throws Exception;
    Optional<User> getUserByUsername(String username);
    Optional<User> getUserByEmail(String email);
    Optional<User> getUserById(Long id);
    User updateUser(Long userId, String newEmail, String newPassword) throws Exception;
    boolean emailExists(String email);
    boolean usernameExists(String username);

}
