package com.crick.livecrick.dto;

public record UserProfileDto(
    Long id,
    String email,
    String name,
    String role,
    Long createdAt
) {
}

