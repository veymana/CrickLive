package com.crick.livecrick.dto;

import java.util.List;

public record UpdateTeamRequest(
        String name,
        String shortName,
        String logo,
        String country,
        List<Long> playerIds
) {}
