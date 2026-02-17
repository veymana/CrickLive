package com.crick.livecrick.service;

import com.crick.livecrick.dto.AuthRequest;
import com.crick.livecrick.dto.AuthResponse;
import com.crick.livecrick.dto.RegisterRequest;
import com.crick.livecrick.dto.UserDto;
import com.crick.livecrick.entity.User;
import com.crick.livecrick.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email already registered");
        }

        User user = new User();
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setName(request.name());
        user.setRole("USER");

        User savedUser = userRepository.save(user);

        String accessToken = jwtService.generateAccessToken(savedUser.getEmail(), savedUser.getRole());
        String refreshToken = jwtService.generateRefreshToken(savedUser.getEmail());

        return new AuthResponse(accessToken, refreshToken, "Bearer", 3600000L, UserDto.from(savedUser));
    }

    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String accessToken = jwtService.generateAccessToken(user.getEmail(), user.getRole());
        String refreshToken = jwtService.generateRefreshToken(user.getEmail());

        return new AuthResponse(accessToken, refreshToken, "Bearer", 3600000L, UserDto.from(user));
    }

    public void logout(String email) {
        // In-memory implementation: tokens are stateless, so logout is implicit
        // In production, you'd invalidate the token in a blacklist
    }

    public boolean verifyToken(String token) {
        return jwtService.isTokenValid(token);
    }

    public AuthResponse refreshToken(String refreshToken) {
        if (!jwtService.isTokenValid(refreshToken)) {
            throw new RuntimeException("Invalid refresh token");
        }

        String email = jwtService.extractEmail(refreshToken);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String newAccessToken = jwtService.generateAccessToken(user.getEmail(), user.getRole());
        String newRefreshToken = jwtService.generateRefreshToken(user.getEmail());

        return new AuthResponse(newAccessToken, newRefreshToken, "Bearer", 3600000L, UserDto.from(user));
    }
}

