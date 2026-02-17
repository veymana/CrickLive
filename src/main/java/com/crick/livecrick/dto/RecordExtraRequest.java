package com.crick.livecrick.dto;

public record RecordExtraRequest(
        int inningsNumber,
        String extraType,
        int runs
) {}
