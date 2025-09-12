package com.bleehouse.Cerberus.controller.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
public class TestController {

    // 인증 없이 접근 가능한 공개 엔드포인트
    @GetMapping("/public")
    public ResponseEntity<Map<String, Object>> publicEndpoint() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Hello! This is a public endpoint.");
        response.put("timestamp", LocalDateTime.now());
        response.put("status", "success");
        response.put("authenticated", false);
        return ResponseEntity.ok(response);
    }

    // 인증이 필요한 보호된 엔드포인트
    @GetMapping("/protected")
    @PreAuthorize("@securityService.hasProtectedAccess()")
    public ResponseEntity<Map<String, Object>> protectedEndpoint() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Hello! This is a protected endpoint.");
        response.put("timestamp", LocalDateTime.now());
        response.put("status", "success");
        response.put("authenticated", true);
        return ResponseEntity.ok(response);
    }

    // POST 요청 테스트용 엔드포인트
    @PostMapping("/echo")
    public ResponseEntity<Map<String, Object>> echoEndpoint(@RequestBody Map<String, Object> requestBody) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Echo endpoint - received your data");
        response.put("timestamp", LocalDateTime.now());
        response.put("receivedData", requestBody);
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    // 사용자 정보 반환 (인증 필요)
    @GetMapping("/user-info")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> userInfo() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "User information endpoint");
        response.put("timestamp", LocalDateTime.now());
        response.put("userRole", "USER");
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    // 관리자 전용 엔드포인트
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> adminEndpoint() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Admin only endpoint");
        response.put("timestamp", LocalDateTime.now());
        response.put("adminAccess", true);
        response.put("status", "success");
        return ResponseEntity.ok(response);
    }

    // 헬스 체크 엔드포인트
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("timestamp", LocalDateTime.now());
        response.put("service", "Cerberus API");
        response.put("version", "1.0-SNAPSHOT");
        return ResponseEntity.ok(response);
    }
}