package com.crick.livecrick.dto;

public record RecordWicketRequest(
        int inningsNumber,
        Long batsmanId,
        Long bowlerId,
        String wicketType
) {}
