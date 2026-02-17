package com.crick.livecrick.dto;

import com.crick.livecrick.entity.Team;

import java.util.List;

public record TeamDto(
        Long id,
        String name,
        String shortName,
        String logo,
        String country,
        List<Long> playerIds,
        TeamStatisticsDto statistics,
        Long createdAt,
        Long updatedAt
) {
    public static TeamDto from(Team team) {
        return new TeamDto(
                team.getId(),
                team.getName(),
                team.getShortName(),
                team.getLogo(),
                team.getCountry(),
                team.getPlayerIds(),
                TeamStatisticsDto.from(team.getStatistics()),
                team.getCreatedAt(),
                team.getUpdatedAt()
        );
    }

    public record TeamStatisticsDto(
            int matchesPlayed,
            int won,
            int lost,
            int tied,
            int noResult
    ) {
        public static TeamStatisticsDto from(Team.TeamStatistics stats) {
            if (stats == null) return null;
            return new TeamStatisticsDto(
                    stats.getMatchesPlayed(),
                    stats.getWon(),
                    stats.getLost(),
                    stats.getTied(),
                    stats.getNoResult()
            );
        }
    }
}
