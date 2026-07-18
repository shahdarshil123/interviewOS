package com.example.interview_os.service;

import com.example.interview_os.dto.AuthResponseDTO;
import com.example.interview_os.dto.LoginRequestDTO;
import com.example.interview_os.dto.RegisterRequestDTO;
import com.example.interview_os.entity.User;
import com.example.interview_os.exception.EmailAlreadyExistsException;
import com.example.interview_os.repository.UserRepository;
import com.example.interview_os.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthServiceImpl authService;

    private RegisterRequestDTO registerRequestDTO;
    private LoginRequestDTO loginRequestDTO;
    private User user;

    @BeforeEach
    void setUp() {
        registerRequestDTO = new RegisterRequestDTO();
        registerRequestDTO.setName("Darsh");
        registerRequestDTO.setEmail("darsh@example.com");
        registerRequestDTO.setPassword("password123");

        loginRequestDTO = new LoginRequestDTO();
        loginRequestDTO.setEmail("darsh@example.com");
        loginRequestDTO.setPassword("password123");

        user = new User();
        user.setId(1L);
        user.setName("Darsh");
        user.setEmail("darsh@example.com");
        user.setPassword("hashedpassword");
    }

    @Test
    @DisplayName("Should register user successfully and return token")
    void shouldRegisterUserSuccessfully() {
        // Arrange
        when(userRepository.findByEmail("darsh@example.com"))
                .thenReturn(Optional.empty());
        when(passwordEncoder.encode("password123"))
                .thenReturn("hashedpassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(jwtService.generateToken(any())).thenReturn("mock.jwt.token");

        // Act
        AuthResponseDTO result = authService.register(registerRequestDTO);

        // Assert
        assertNotNull(result);
        assertEquals("mock.jwt.token", result.getToken());
        assertEquals("darsh@example.com", result.getEmail());
        verify(passwordEncoder, times(1)).encode("password123");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("Should throw EmailAlreadyExistsException when email is taken")
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        // Arrange
        when(userRepository.findByEmail("darsh@example.com"))
                .thenReturn(Optional.of(user));

        // Act and Assert
        assertThrows(EmailAlreadyExistsException.class,
                () -> authService.register(registerRequestDTO));

        verify(userRepository, never()).save(any());
        verify(passwordEncoder, never()).encode(any());
    }

    @Test
    @DisplayName("Should login successfully and return token")
    void shouldLoginSuccessfully() {
        // Arrange
        when(authenticationManager.authenticate(
                any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null);
        when(userRepository.findByEmail("darsh@example.com"))
                .thenReturn(Optional.of(user));
        when(jwtService.generateToken(any())).thenReturn("mock.jwt.token");

        // Act
        AuthResponseDTO result = authService.login(loginRequestDTO);

        // Assert
        assertNotNull(result);
        assertEquals("mock.jwt.token", result.getToken());
        assertEquals("darsh@example.com", result.getEmail());
        verify(authenticationManager, times(1)).authenticate(any());
    }

    @Test
    @DisplayName("Should verify password is encoded before saving")
    void shouldEncodePasswordBeforeSaving() {
        // Arrange
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
        when(passwordEncoder.encode("password123")).thenReturn("hashedpassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(jwtService.generateToken(any())).thenReturn("mock.jwt.token");

        // Act
        authService.register(registerRequestDTO);

        // Assert
        verify(passwordEncoder, times(1)).encode("password123");
        verify(userRepository, times(1)).save(argThat(savedUser ->
                savedUser.getPassword().equals("hashedpassword")));
    }
}