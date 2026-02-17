package com.crick.livecrick.controller;

import com.crick.livecrick.dto.FiltersDto;
import com.crick.livecrick.dto.SearchResultDto;
import com.crick.livecrick.dto.TopPerformersDto;
import com.crick.livecrick.service.SearchService;
import com.crick.livecrick.service.StatisticsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StatisticsController {

    private final StatisticsService statisticsService;
    private final SearchService searchService;

    public StatisticsController(StatisticsService statisticsService, SearchService searchService) {
        this.statisticsService = statisticsService;
        this.searchService = searchService;
    }

    @GetMapping("/statistics/players/top")
    public ResponseEntity<TopPerformersDto> getTopPerformers(
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(statisticsService.getTopPerformers(limit));
    }

    @GetMapping("/search")
    public ResponseEntity<List<SearchResultDto>> search(
            @RequestParam String q,
            @RequestParam(required = false) String type) {
        return ResponseEntity.ok(searchService.search(q, type));
    }

    @GetMapping("/filters/countries")
    public ResponseEntity<List<String>> getCountries() {
        return ResponseEntity.ok(searchService.getCountries());
    }

    @GetMapping("/filters/roles")
    public ResponseEntity<List<String>> getRoles() {
        return ResponseEntity.ok(searchService.getRoles());
    }

    @GetMapping("/filters/venues")
    public ResponseEntity<List<String>> getVenues() {
        return ResponseEntity.ok(searchService.getVenues());
    }

    @GetMapping("/filters")
    public ResponseEntity<FiltersDto> getAllFilters() {
        return ResponseEntity.ok(new FiltersDto(
                searchService.getCountries(),
                searchService.getRoles(),
                searchService.getVenues()
        ));
    }
}
