package com.crick.livecrick.dto;

import java.util.List;

public record TopPerformersDto(
        List<PlayerPerformance> topRunScorers,
        List<PlayerPerformance> topWicketTakers,
        List<PlayerPerformance> bestAverages
) {
    public record PlayerPerformance(
            Long playerId,
            String playerName,
            int value,
            String metric
    ) {}
}
