package com.example.interview_os.controller;

import com.example.interview_os.dto.AuthResponseDTO;
import com.example.interview_os.dto.LoginRequestDTO;
import com.example.interview_os.dto.RegisterRequestDTO;
import com.example.interview_os.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(
            @Valid @RequestBody RegisterRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authService.register(requestDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO requestDTO) {
        return ResponseEntity.ok(authService.login(requestDTO));
    }
}

