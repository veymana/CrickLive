package com.crick.livecrick.controller;

import com.crick.livecrick.dto.CreatePlayerRequest;
import com.crick.livecrick.dto.PlayerDto;
import com.crick.livecrick.dto.UpdatePlayerRequest;
import com.crick.livecrick.service.PlayerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/players")
public class PlayerController {

    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public ResponseEntity<List<PlayerDto>> getAllPlayers(
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String role,
            @RequestParam(required = false) Long teamId,
            @RequestParam(required = false) String search) {
        
        List<PlayerDto> players;
        
        if (search != null && !search.isEmpty()) {
            players = playerService.searchPlayers(search);
        } else if (country != null && !country.isEmpty()) {
            players = playerService.getPlayersByCountry(country);
        } else if (role != null && !role.isEmpty()) {
            players = playerService.getPlayersByRole(role);
        } else if (teamId != null) {
            players = playerService.getPlayersByTeam(teamId);
        } else {
            players = playerService.getAllPlayers();
        }
        
        return ResponseEntity.ok(players);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerDto> getPlayer(@PathVariable Long id) {
        return ResponseEntity.ok(playerService.getPlayer(id));
    }

    @PostMapping
    public ResponseEntity<PlayerDto> createPlayer(@RequestBody CreatePlayerRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(playerService.createPlayer(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PlayerDto> updatePlayer(
            @PathVariable Long id,
            @RequestBody UpdatePlayerRequest request) {
        return ResponseEntity.ok(playerService.updatePlayer(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deletePlayer(@PathVariable Long id) {
        playerService.deletePlayer(id);
        return ResponseEntity.ok(new MessageResponse("Player deleted successfully"));
    }

    @GetMapping("/{id}/statistics")
    public ResponseEntity<PlayerDto> getPlayerStatistics(@PathVariable Long id) {
        return ResponseEntity.ok(playerService.getPlayer(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<PlayerDto>> searchPlayers(@RequestParam String q) {
        return ResponseEntity.ok(playerService.searchPlayers(q));
    }

    public record MessageResponse(String message) {}
}
