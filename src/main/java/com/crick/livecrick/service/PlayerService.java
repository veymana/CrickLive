package com.crick.livecrick.service;

import com.crick.livecrick.dto.CreatePlayerRequest;
import com.crick.livecrick.dto.PlayerDto;
import com.crick.livecrick.dto.UpdatePlayerRequest;
import com.crick.livecrick.entity.Player;
import com.crick.livecrick.repository.PlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public PlayerDto createPlayer(CreatePlayerRequest request) {
        Player player = new Player();
        player.setName(request.name());
        player.setPhoto(request.photo());
        // Set defaults for dateOfBirth and country if not provided
        player.setDateOfBirth(request.dateOfBirth() != null ? request.dateOfBirth() : "1990-01-01");
        player.setCountry(request.country() != null ? request.country() : "Unknown");
        player.setRole(request.role());
        player.setBattingStyle(request.battingStyle());
        player.setBowlingStyle(request.bowlingStyle());
        player.setTeamId(request.teamId());
        
        // Always initialize statistics with defaults
        Player.PlayerStatistics stats = new Player.PlayerStatistics();
        if (request.statistics() != null) {
            stats.setMatches(request.statistics().matches());
            stats.setInnings(request.statistics().innings());
            stats.setRuns(request.statistics().runs());
            stats.setAverage(request.statistics().average());
            stats.setStrikeRate(request.statistics().strikeRate());
            stats.setHighestScore(request.statistics().highestScore());
            stats.setHundreds(request.statistics().hundreds());
            stats.setFifties(request.statistics().fifties());
            stats.setWickets(request.statistics().wickets());
            stats.setBowlingAverage(request.statistics().bowlingAverage());
            stats.setEconomy(request.statistics().economy());
            stats.setBestBowling(request.statistics().bestBowling());
        } else {
            // Set all to zero/empty if no statistics provided
            stats.setMatches(0);
            stats.setInnings(0);
            stats.setRuns(0);
            stats.setAverage(0.0);
            stats.setStrikeRate(0.0);
            stats.setHighestScore(0);
            stats.setHundreds(0);
            stats.setFifties(0);
            stats.setWickets(0);
            stats.setBowlingAverage(0.0);
            stats.setEconomy(0.0);
            stats.setBestBowling("");
        }
        player.setStatistics(stats);

        Player saved = playerRepository.save(player);
        return PlayerDto.from(saved);
    }

    public PlayerDto getPlayer(Long id) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Player not found with id: " + id));
        return PlayerDto.from(player);
    }

    public List<PlayerDto> getAllPlayers() {
        return playerRepository.findAll().stream()
                .map(PlayerDto::from)
                .collect(Collectors.toList());
    }

    public List<PlayerDto> getPlayersByCountry(String country) {
        return playerRepository.findByCountry(country).stream()
                .map(PlayerDto::from)
                .collect(Collectors.toList());
    }

    public List<PlayerDto> getPlayersByRole(String role) {
        return playerRepository.findByRole(role).stream()
                .map(PlayerDto::from)
                .collect(Collectors.toList());
    }

    public List<PlayerDto> getPlayersByTeam(Long teamId) {
        return playerRepository.findByTeamId(teamId).stream()
                .map(PlayerDto::from)
                .collect(Collectors.toList());
    }

    public List<PlayerDto> searchPlayers(String query) {
        return playerRepository.searchByName(query).stream()
                .map(PlayerDto::from)
                .collect(Collectors.toList());
    }

    public PlayerDto updatePlayer(Long id, UpdatePlayerRequest request) {
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Player not found with id: " + id));

        if (request.name() != null) player.setName(request.name());
        if (request.photo() != null) player.setPhoto(request.photo());
        if (request.dateOfBirth() != null) player.setDateOfBirth(request.dateOfBirth());
        if (request.country() != null) player.setCountry(request.country());
        if (request.role() != null) player.setRole(request.role());
        if (request.battingStyle() != null) player.setBattingStyle(request.battingStyle());
        if (request.bowlingStyle() != null) player.setBowlingStyle(request.bowlingStyle());
        if (request.teamId() != null) player.setTeamId(request.teamId());

        if (request.statistics() != null) {
            Player.PlayerStatistics stats = player.getStatistics();
            if (stats == null) {
                stats = new Player.PlayerStatistics();
            }
            stats.setMatches(request.statistics().matches());
            stats.setInnings(request.statistics().innings());
            stats.setRuns(request.statistics().runs());
            stats.setAverage(request.statistics().average());
            stats.setStrikeRate(request.statistics().strikeRate());
            stats.setHighestScore(request.statistics().highestScore());
            stats.setHundreds(request.statistics().hundreds());
            stats.setFifties(request.statistics().fifties());
            stats.setWickets(request.statistics().wickets());
            stats.setBowlingAverage(request.statistics().bowlingAverage());
            stats.setEconomy(request.statistics().economy());
            stats.setBestBowling(request.statistics().bestBowling());
            player.setStatistics(stats);
        }

        Player updated = playerRepository.save(player);
        return PlayerDto.from(updated);
    }

    public void deletePlayer(Long id) {
        if (!playerRepository.existsById(id)) {
            throw new RuntimeException("Player not found with id: " + id);
        }
        playerRepository.deleteById(id);
    }

    public List<String> getAllCountries() {
        return playerRepository.findAll().stream()
                .map(Player::getCountry)
                .filter(country -> country != null && !country.isEmpty())
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }

    public List<String> getAllRoles() {
        return List.of("BATSMAN", "BOWLER", "ALL_ROUNDER", "WICKET_KEEPER");
    }
}
