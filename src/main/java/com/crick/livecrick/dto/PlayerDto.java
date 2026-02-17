package com.crick.livecrick.dto;

import com.crick.livecrick.entity.Player;

public record PlayerDto(
        Long id,
        String name,
        String photo,
        String dateOfBirth,
        String country,
        String role,
        String battingStyle,
        String bowlingStyle,
        Long teamId,
        PlayerStatisticsDto statistics,
        Long createdAt,
        Long updatedAt
) {
    public static PlayerDto from(Player player) {
        return new PlayerDto(
                player.getId(),
                player.getName(),
                player.getPhoto(),
                player.getDateOfBirth(),
                player.getCountry(),
                player.getRole(),
                player.getBattingStyle(),
                player.getBowlingStyle(),
                player.getTeamId(),
                PlayerStatisticsDto.from(player.getStatistics()),
                player.getCreatedAt(),
                player.getUpdatedAt()
        );
    }

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
    ) {
        public static PlayerStatisticsDto from(Player.PlayerStatistics stats) {
            if (stats == null) return null;
            return new PlayerStatisticsDto(
                    stats.getMatches(),
                    stats.getInnings(),
                    stats.getRuns(),
                    stats.getAverage(),
                    stats.getStrikeRate(),
                    stats.getHighestScore(),
                    stats.getHundreds(),
                    stats.getFifties(),
                    stats.getWickets(),
                    stats.getBowlingAverage(),
                    stats.getEconomy(),
                    stats.getBestBowling()
            );
        }
    }
}
