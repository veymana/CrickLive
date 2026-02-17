package com.crick.livecrick.service;

import com.crick.livecrick.dto.TopPerformersDto;
import com.crick.livecrick.entity.Player;
import com.crick.livecrick.repository.MatchRepository;
import com.crick.livecrick.repository.PlayerRepository;
import com.crick.livecrick.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatisticsService {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final MatchRepository matchRepository;

    public StatisticsService(PlayerRepository playerRepository, TeamRepository teamRepository, MatchRepository matchRepository) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
        this.matchRepository = matchRepository;
    }

    public TopPerformersDto getTopPerformers(int limit) {
        List<Player> allPlayers = playerRepository.findAll();

        List<TopPerformersDto.PlayerPerformance> topRunScorers = allPlayers.stream()
                .sorted(Comparator.comparingInt((Player p) -> p.getStatistics().getRuns()).reversed())
                .limit(limit)
                .map(p -> new TopPerformersDto.PlayerPerformance(
                        p.getId(),
                        p.getName(),
                        p.getStatistics().getRuns(),
                        "runs"
                ))
                .collect(Collectors.toList());

        List<TopPerformersDto.PlayerPerformance> topWicketTakers = allPlayers.stream()
                .filter(p -> p.getStatistics().getWickets() > 0)
                .sorted(Comparator.comparingInt((Player p) -> p.getStatistics().getWickets()).reversed())
                .limit(limit)
                .map(p -> new TopPerformersDto.PlayerPerformance(
                        p.getId(),
                        p.getName(),
                        p.getStatistics().getWickets(),
                        "wickets"
                ))
                .collect(Collectors.toList());

        List<TopPerformersDto.PlayerPerformance> bestAverages = allPlayers.stream()
                .filter(p -> p.getStatistics().getAverage() > 0)
                .sorted(Comparator.comparingDouble((Player p) -> p.getStatistics().getAverage()).reversed())
                .limit(limit)
                .map(p -> new TopPerformersDto.PlayerPerformance(
                        p.getId(),
                        p.getName(),
                        (int) p.getStatistics().getAverage(),
                        "average"
                ))
                .collect(Collectors.toList());

        return new TopPerformersDto(topRunScorers, topWicketTakers, bestAverages);
    }
}
