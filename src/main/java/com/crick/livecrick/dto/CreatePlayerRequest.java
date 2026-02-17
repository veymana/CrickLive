package com.crick.livecrick.dto;

import com.crick.livecrick.entity.Player;

public record CreatePlayerRequest(
        String name,
        String photo,
        String dateOfBirth,
        String country,
        String role,
        String battingStyle,
        String bowlingStyle,
        Long teamId,
        PlayerStatisticsDto statistics
) {
    public record PlayerStatisticsDto(
            int matches,
            int innings,
            int runs,
            double average,
            double strikeRate,
            int highestScore,
            int hundreds,
            int fifties,
            int wickets,
            double bowlingAverage,
            double economy,
            String bestBowling
    ) {}
}
