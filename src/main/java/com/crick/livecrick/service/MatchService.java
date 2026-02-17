package com.crick.livecrick.service;

import com.crick.livecrick.dto.CreateMatchRequest;
import com.crick.livecrick.dto.MatchDto;
import com.crick.livecrick.dto.UpdateMatchRequest;
import com.crick.livecrick.entity.Match;
import com.crick.livecrick.repository.MatchRepository;
import com.crick.livecrick.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;

    public MatchService(MatchRepository matchRepository, TeamRepository teamRepository) {
        this.matchRepository = matchRepository;
        this.teamRepository = teamRepository;
    }

    public MatchDto createMatch(CreateMatchRequest request) {
        Match match = new Match();
        match.setTeam1Id(request.team1Id());
        match.setTeam2Id(request.team2Id());
        match.setTeam1Name(request.team1Name());
        match.setTeam2Name(request.team2Name());
        match.setMatchType(request.matchType());
        match.setVenue(request.venue());
        match.setMatchDate(request.matchDate());
        match.setStatus("UPCOMING");

        Match saved = matchRepository.save(match);
        return MatchDto.from(saved);
    }

    public MatchDto getMatch(Long id) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Match not found with id: " + id));
        return MatchDto.from(match);
    }

    public List<MatchDto> getAllMatches(String status, Long teamId) {
        List<Match> matches;

        if (status != null && !status.isEmpty()) {
            matches = matchRepository.findByStatus(status);
        } else if (teamId != null) {
            matches = matchRepository.findByTeamId(teamId);
        } else {
            matches = matchRepository.findAll();
        }

        return matches.stream()
                .map(MatchDto::from)
                .collect(Collectors.toList());
    }

    public List<MatchDto> getLiveMatches() {
        return matchRepository.findLiveMatches().stream()
                .map(MatchDto::from)
                .collect(Collectors.toList());
    }

    public List<MatchDto> getUpcomingMatches() {
        return matchRepository.findUpcomingMatches().stream()
                .map(MatchDto::from)
                .collect(Collectors.toList());
    }

    public List<MatchDto> getMatchHistory() {
        return matchRepository.findCompletedMatches().stream()
                .map(MatchDto::from)
                .collect(Collectors.toList());
    }

    public MatchDto updateMatch(Long id, UpdateMatchRequest request) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Match not found with id: " + id));

        if (request.team1Id() != null) match.setTeam1Id(request.team1Id());
        if (request.team2Id() != null) match.setTeam2Id(request.team2Id());
        if (request.team1Name() != null) match.setTeam1Name(request.team1Name());
        if (request.team2Name() != null) match.setTeam2Name(request.team2Name());
        if (request.matchType() != null) match.setMatchType(request.matchType());
        if (request.venue() != null) match.setVenue(request.venue());
        if (request.matchDate() != null) match.setMatchDate(request.matchDate());
        if (request.status() != null) match.setStatus(request.status());
        if (request.result() != null) match.setResult(request.result());
        if (request.winnerTeamId() != null) match.setWinnerTeamId(request.winnerTeamId());

        Match updated = matchRepository.save(match);
        return MatchDto.from(updated);
    }

    public MatchDto updateStatus(Long id, String status) {
        Match match = matchRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Match not found with id: " + id));
        
        match.setStatus(status);
        Match updated = matchRepository.save(match);
        return MatchDto.from(updated);
    }

    public void deleteMatch(Long id) {
        if (!matchRepository.existsById(id)) {
            throw new RuntimeException("Match not found with id: " + id);
        }
        matchRepository.deleteById(id);
    }

    public MatchDto getScoreboard(Long matchId) {
        return getMatch(matchId);
    }
}
