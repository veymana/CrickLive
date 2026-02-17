package com.crick.livecrick.dto;

import java.util.List;

public record FiltersDto(
        List<String> countries,
        List<String> roles,
        List<String> venues
) {}
