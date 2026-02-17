package com.crick.livecrick.controller;

import com.crick.livecrick.dto.*;
import com.crick.livecrick.service.TeamService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teams")
public class TeamController {

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    public ResponseEntity<List<TeamDto>> getAllTeams(
            @RequestParam(required = false) String country) {
        
        List<TeamDto> teams;
        
        if (country != null && !country.isEmpty()) {
            teams = teamService.getTeamsByCountry(country);
        } else {
            teams = teamService.getAllTeams();
        }
        
        return ResponseEntity.ok(teams);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamDto> getTeam(@PathVariable Long id) {
        return ResponseEntity.ok(teamService.getTeam(id));
    }

    @PostMapping
    public ResponseEntity<TeamDto> createTeam(@RequestBody CreateTeamRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(teamService.createTeam(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeamDto> updateTeam(
            @PathVariable Long id,
            @RequestBody UpdateTeamRequest request) {
        return ResponseEntity.ok(teamService.updateTeam(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);
        return ResponseEntity.ok(new MessageResponse("Team deleted successfully"));
    }

    @GetMapping("/{id}/players")
    public ResponseEntity<List<PlayerDto>> getTeamPlayers(@PathVariable Long id) {
        return ResponseEntity.ok(teamService.getTeamPlayers(id));
    }

    @GetMapping("/{id}/statistics")
    public ResponseEntity<TeamDto.TeamStatisticsDto> getTeamStatistics(@PathVariable Long id) {
        return ResponseEntity.ok(teamService.getTeamStatistics(id));
    }

    public record MessageResponse(String message) {}
}
