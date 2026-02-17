package com.crick.livecrick.controller;

import com.crick.livecrick.dto.AuthRequest;
import com.crick.livecrick.dto.AuthResponse;
import com.crick.livecrick.dto.RegisterRequest;
import com.crick.livecrick.service.AuthService;
import com.crick.livecrick.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JwtService jwtService;

    public AuthController(AuthService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<MessageResponse> logout(@RequestHeader("Authorization") String token) {
        String actualToken = token.replace("Bearer ", "");
        String email = jwtService.extractEmail(actualToken);
        authService.logout(email);
        return ResponseEntity.ok(new MessageResponse("Logged out successfully"));
    }

    @GetMapping("/verify")
    public ResponseEntity<VerifyResponse> verify(@RequestHeader("Authorization") String token) {
        String actualToken = token.replace("Bearer ", "");
        boolean isValid = authService.verifyToken(actualToken);
        return ResponseEntity.ok(new VerifyResponse(isValid));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestHeader("Authorization") String token) {
        String actualToken = token.replace("Bearer ", "");
        return ResponseEntity.ok(authService.refreshToken(actualToken));
    }

    public record MessageResponse(String message) {
    }

    public record VerifyResponse(boolean valid) {
    }
}

