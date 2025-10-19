package com.blogapp.blogappbackend.service;

import com.blogapp.blogappbackend.entity.User;
import com.blogapp.blogappbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImplementation implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public User registerUser(User user) throws Exception {
        // Check if email or username already exists
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new Exception("Email already registered");
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new Exception("Username already taken");
        }

        // Hash the password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Save user
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User updateUser(Long userId, String newEmail, String newPassword) throws Exception {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new Exception("User not found");
        }

        User user = optionalUser.get();

        if (newEmail != null && !newEmail.equals(user.getEmail())) {
            if (userRepository.existsByEmail(newEmail)) {
                throw new Exception("Email already in use");
            }
            user.setEmail(newEmail);
        }

        if (newPassword != null && !newPassword.isBlank()) {
            user.setPassword(passwordEncoder.encode(newPassword));
        }

        return userRepository.save(user);
    }

    @Override
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }
}
