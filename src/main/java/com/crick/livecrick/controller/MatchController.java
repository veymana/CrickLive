package com.crick.livecrick.controller;

import com.crick.livecrick.dto.CreateMatchRequest;
import com.crick.livecrick.dto.MatchDto;
import com.crick.livecrick.dto.UpdateMatchRequest;
import com.crick.livecrick.dto.UpdateStatusRequest;
import com.crick.livecrick.service.MatchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matches")
public class MatchController {

    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping
    public ResponseEntity<List<MatchDto>> getAllMatches(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long teamId) {
        return ResponseEntity.ok(matchService.getAllMatches(status, teamId));
    }

    @GetMapping("/live")
    public ResponseEntity<List<MatchDto>> getLiveMatches() {
        return ResponseEntity.ok(matchService.getLiveMatches());
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<MatchDto>> getUpcomingMatches() {
        return ResponseEntity.ok(matchService.getUpcomingMatches());
    }

    @GetMapping("/history")
    public ResponseEntity<List<MatchDto>> getMatchHistory() {
        return ResponseEntity.ok(matchService.getMatchHistory());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatchDto> getMatch(@PathVariable Long id) {
        return ResponseEntity.ok(matchService.getMatch(id));
    }

    @GetMapping("/{id}/scoreboard")
    public ResponseEntity<MatchDto> getScoreboard(@PathVariable Long id) {
        return ResponseEntity.ok(matchService.getScoreboard(id));
    }

    @PostMapping
    public ResponseEntity<MatchDto> createMatch(@RequestBody CreateMatchRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(matchService.createMatch(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MatchDto> updateMatch(
            @PathVariable Long id,
            @RequestBody UpdateMatchRequest request) {
        return ResponseEntity.ok(matchService.updateMatch(id, request));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<MatchDto> updateStatus(
            @PathVariable Long id,
            @RequestBody UpdateStatusRequest request) {
        return ResponseEntity.ok(matchService.updateStatus(id, request.status()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteMatch(@PathVariable Long id) {
        matchService.deleteMatch(id);
        return ResponseEntity.ok(new MessageResponse("Match deleted successfully"));
    }

    public record MessageResponse(String message) {}
}
