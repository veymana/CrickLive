package com.crick.livecrick.dto;

public record StartInningsRequest(
        int inningsNumber,
        Long battingTeamId,
        Long bowlingTeamId,
        Long openingBatsman1Id,
        Long openingBatsman2Id,
        Long openingBowlerId
) {}
