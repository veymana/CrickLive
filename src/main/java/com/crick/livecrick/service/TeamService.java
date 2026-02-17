package com.crick.livecrick.service;

import com.crick.livecrick.dto.CreateTeamRequest;
import com.crick.livecrick.dto.PlayerDto;
import com.crick.livecrick.dto.TeamDto;
import com.crick.livecrick.dto.UpdateTeamRequest;
import com.crick.livecrick.entity.Team;
import com.crick.livecrick.repository.MatchRepository;
import com.crick.livecrick.repository.PlayerRepository;
import com.crick.livecrick.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final MatchRepository matchRepository;

    public TeamService(TeamRepository teamRepository, PlayerRepository playerRepository, MatchRepository matchRepository) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
        this.matchRepository = matchRepository;
    }

    public TeamDto createTeam(CreateTeamRequest request) {
        Team team = new Team();
        team.setName(request.name());
        team.setShortName(request.shortName());
        team.setLogo(request.logo());
        team.setCountry(request.country());
        
        if (request.playerIds() != null) {
            team.setPlayerIds(new ArrayList<>(request.playerIds()));
        }

        Team saved = teamRepository.save(team);
        return TeamDto.from(saved);
    }

    public TeamDto getTeam(Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Team not found with id: " + id));
        return TeamDto.from(team);
    }

    public List<TeamDto> getAllTeams() {
        return teamRepository.findAll().stream()
                .map(TeamDto::from)
                .collect(Collectors.toList());
    }

    public List<TeamDto> getTeamsByCountry(String country) {
        return teamRepository.findByCountry(country).stream()
                .map(TeamDto::from)
                .collect(Collectors.toList());
    }

    public TeamDto updateTeam(Long id, UpdateTeamRequest request) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Team not found with id: " + id));

        if (request.name() != null) team.setName(request.name());
        if (request.shortName() != null) team.setShortName(request.shortName());
        if (request.logo() != null) team.setLogo(request.logo());
        if (request.country() != null) team.setCountry(request.country());
        if (request.playerIds() != null) team.setPlayerIds(new ArrayList<>(request.playerIds()));

        Team updated = teamRepository.save(team);
        return TeamDto.from(updated);
    }

    public void deleteTeam(Long id) {
        if (!teamRepository.existsById(id)) {
            throw new RuntimeException("Team not found with id: " + id);
        }
        teamRepository.deleteById(id);
    }

    public List<PlayerDto> getTeamPlayers(Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found with id: " + teamId));
        
        // Use the team's playerIds list to get players
        if (team.getPlayerIds() == null || team.getPlayerIds().isEmpty()) {
            return new ArrayList<>();
        }
        
        return team.getPlayerIds().stream()
                .map(playerId -> playerRepository.findById(playerId))
                .filter(optionalPlayer -> optionalPlayer.isPresent())
                .map(optionalPlayer -> PlayerDto.from(optionalPlayer.get()))
                .collect(Collectors.toList());
    }

    public TeamDto.TeamStatisticsDto getTeamStatistics(Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found with id: " + teamId));
        return TeamDto.TeamStatisticsDto.from(team.getStatistics());
    }
}
