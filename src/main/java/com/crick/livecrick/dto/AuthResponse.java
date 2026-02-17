package com.crick.livecrick.dto;

public record AuthResponse(
    String token,
    String refreshToken,
    String tokenType,
    Long expiresIn,
    UserDto user
) {
}

