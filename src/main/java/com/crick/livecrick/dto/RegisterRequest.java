package com.crick.livecrick.dto;

public record RegisterRequest(
    String email,
    String password,
    String name
) {
}

