package com.crick.livecrick.dto;

public record AuthRequest(
    String email,
    String password
) {
}

