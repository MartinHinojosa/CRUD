package com.crud.crud.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class HealthController {
    
    @GetMapping("/health")
    public ResponseEntity<?> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "OK");
        response.put("message", "Backend está funcionando correctamente");
        return ResponseEntity.ok(response);
    }
}

@RestController
@CrossOrigin(origins = "*")
class RootController {
    
    @GetMapping("/")
    public ResponseEntity<?> root() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Backend CRUD API está funcionando");
        response.put("version", "1.0.0");
        response.put("endpoints", new HashMap<String, String>() {{
            put("health", "/api/health");
            put("login", "POST /api/users/login");
            put("user profile", "GET /api/users/{id}");
            put("dashboard", "GET /api/dashboard/user/{userId}");
        }});
        return ResponseEntity.ok(response);
    }
}

