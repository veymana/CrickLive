package com.crick.livecrick.dto;

public record SearchResultDto(
        String type,
        Long id,
        String name,
        String description
) {}
