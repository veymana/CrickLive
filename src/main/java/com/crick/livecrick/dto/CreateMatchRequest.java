package com.crick.livecrick.dto;

public record CreateMatchRequest(
        Long team1Id,
        Long team2Id,
        String team1Name,
        String team2Name,
        String matchType,
        String venue,
        Long matchDate
) {}
