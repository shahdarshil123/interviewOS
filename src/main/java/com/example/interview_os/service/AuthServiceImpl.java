package com.example.interview_os.service;

import com.example.interview_os.dto.AuthResponseDTO;
import com.example.interview_os.dto.LoginRequestDTO;
import com.example.interview_os.dto.RegisterRequestDTO;
import com.example.interview_os.entity.User;
import com.example.interview_os.exception.EmailAlreadyExistsException;
import com.example.interview_os.repository.UserRepository;
import com.example.interview_os.security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JwtService jwtService,
                           AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthResponseDTO register(RegisterRequestDTO requestDTO) {
        System.out.println("Step 1: Checking email");
        if (userRepository.findByEmail(requestDTO.getEmail()).isPresent()) {
            throw new EmailAlreadyExistsException(requestDTO.getEmail());
        }

        System.out.println("Step 2: Creating user");
        User user = new User();
        user.setName(requestDTO.getName());
        user.setEmail(requestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));

        System.out.println("Step 3: Saving user");
        userRepository.save(user);

        System.out.println("Step 4: Building UserDetails");
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles("USER")
                .build();

        System.out.println("Step 5: Generating token");
        String token = jwtService.generateToken(userDetails);

        System.out.println("Step 6: Returning response");
        return new AuthResponseDTO(token, user.getEmail(), user.getName());
    }

    @Override
    public AuthResponseDTO login(LoginRequestDTO requestDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        requestDTO.getEmail(),
                        requestDTO.getPassword()
                )
        );

        User user = userRepository.findByEmail(requestDTO.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles("USER")
                .build();

        String token = jwtService.generateToken(userDetails);
        return new AuthResponseDTO(token, user.getEmail(), user.getName());
    }
}