package com.crick.livecrick.controller;

import com.crick.livecrick.dto.UpdateProfileRequest;
import com.crick.livecrick.dto.UserProfileDto;
import com.crick.livecrick.service.JwtService;
import com.crick.livecrick.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfileDto> getProfile(@RequestHeader("Authorization") String token) {
        String actualToken = token.replace("Bearer ", "");
        String email = jwtService.extractEmail(actualToken);
        return ResponseEntity.ok(userService.getProfile(email));
    }

    @PutMapping("/profile")
    public ResponseEntity<UserProfileDto> updateProfile(
            @RequestHeader("Authorization") String token,
            @RequestBody UpdateProfileRequest request) {
        String actualToken = token.replace("Bearer ", "");
        String email = jwtService.extractEmail(actualToken);
        return ResponseEntity.ok(userService.updateProfile(email, request));
    }
}

