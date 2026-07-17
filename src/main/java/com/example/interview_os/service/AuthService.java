package com.example.interview_os.service;

import com.example.interview_os.dto.AuthResponseDTO;
import com.example.interview_os.dto.LoginRequestDTO;
import com.example.interview_os.dto.RegisterRequestDTO;

public interface AuthService {
    AuthResponseDTO register(RegisterRequestDTO requestDTO);
    AuthResponseDTO login(LoginRequestDTO requestDTO);
}