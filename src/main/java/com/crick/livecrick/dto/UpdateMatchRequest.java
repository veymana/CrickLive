package com.crick.livecrick.dto;

public record UpdateMatchRequest(
        Long team1Id,
        Long team2Id,
        String team1Name,
        String team2Name,
        String matchType,
        String venue,
        Long matchDate,
        String status,
        String result,
        Long winnerTeamId
) {}
