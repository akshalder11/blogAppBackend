package com.blogapp.blogappbackend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HealthController {

    @GetMapping("/api/health")
    public ResponseEntity<?> healthCheck () {
        return ResponseEntity.ok().body(Map.of("status", "Backend is up and running"));
    }
}
