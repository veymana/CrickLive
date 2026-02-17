package com.crick.livecrick.dto;

public record RecordBallRequest(
        int inningsNumber,
        Long batsmanId,
        Long bowlerId,
        int runs,
        boolean isWicket,
        String wicketType,
        String extraType,
        int extraRuns
) {}
