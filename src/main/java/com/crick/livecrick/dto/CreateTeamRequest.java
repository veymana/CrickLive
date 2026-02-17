package com.crick.livecrick.dto;

import java.util.List;

public record CreateTeamRequest(
        String name,
        String shortName,
        String logo,
        String country,
        List<Long> playerIds
) {}
