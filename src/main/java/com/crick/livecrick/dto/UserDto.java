package com.crick.livecrick.dto;

import com.crick.livecrick.entity.User;

public record UserDto(
    String id,
    String username,
    String email,
    String role
) {
    public static UserDto from(User user) {
        return new UserDto(
            user.getId() != null ? user.getId().toString() : null,
            user.getName(),  // Use name as username for frontend compatibility
            user.getEmail(),
            user.getRole().toLowerCase()  // Convert to lowercase for frontend
        );
    }
}
