package com.crick.livecrick.dto;

public record AdminDashboardDto(
    Long totalUsers,
    Long adminUsers,
    Long regularUsers,
    Long activeUsers
) {
}

