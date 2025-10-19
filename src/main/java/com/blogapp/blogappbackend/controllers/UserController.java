package com.blogapp.blogappbackend.controllers;

import com.blogapp.blogappbackend.entity.User;
import com.blogapp.blogappbackend.service.UserService;
import com.blogapp.blogappbackend.utilities.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtService jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // REGISTER
    @PostMapping("/registerUser")
    public ResponseEntity<?> registerUser (@RequestBody User user) {
        try {
            User saveUser = userService.registerUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(saveUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // LOGIN
    @PostMapping("/loginUser")
    public ResponseEntity<?> loginUser (@RequestBody User userBody) {
        Optional<User> loginUser = userService.getUserByUsername(userBody.getUsername());
        if (loginUser.isPresent() && passwordEncoder.matches(userBody.getPassword(), loginUser.get().getPassword())) {
            String jwtToken = jwtUtil.generateToken(loginUser.get().getUsername(), loginUser.get().getId());
            return ResponseEntity.ok().body(Map.of(
                    "username", loginUser.get().getUsername(),
                    "jwtToken", jwtToken
            ));
        } else
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid Credentials"));
    }


    // UPDATE EMAIL/PASSWORD
    @PutMapping("/updateUser")
    public ResponseEntity<?> updateUser (@RequestBody Map<String, String> payload) {
        try {
            Long userId = Long.parseLong(payload.get("userId"));
            String newEmail = payload.get("email");
            String newPassword = payload.get("password");

            if ((newEmail == null || newEmail.isBlank()) && (newPassword == null || newPassword.isBlank())) {
                return ResponseEntity.badRequest().body(Map.of("error", "Nothing to update"));
            }

            User updateUser = userService.updateUser(userId, newEmail, newPassword);
            return ResponseEntity.ok(updateUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }


}
