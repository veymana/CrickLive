package com.crick.livecrick.service;

import com.crick.livecrick.dto.SearchResultDto;
import com.crick.livecrick.entity.Match;
import com.crick.livecrick.entity.Player;
import com.crick.livecrick.entity.Team;
import com.crick.livecrick.repository.MatchRepository;
import com.crick.livecrick.repository.PlayerRepository;
import com.crick.livecrick.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchService {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final MatchRepository matchRepository;

    public SearchService(PlayerRepository playerRepository, TeamRepository teamRepository, MatchRepository matchRepository) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
        this.matchRepository = matchRepository;
    }

    public List<SearchResultDto> search(String query, String type) {
        List<SearchResultDto> results = new ArrayList<>();
        String lowerQuery = query.toLowerCase();

        if (type == null || type.equals("all") || type.equals("player")) {
            List<Player> players = playerRepository.searchByName(query);
            for (Player player : players) {
                results.add(new SearchResultDto(
                        "player",
                        player.getId(),
                        player.getName(),
                        player.getRole() + " - " + player.getCountry()
                ));
            }
        }

        if (type == null || type.equals("all") || type.equals("team")) {
            List<Team> teams = teamRepository.findAll();
            for (Team team : teams) {
                if (team.getName().toLowerCase().contains(lowerQuery) ||
                    (team.getShortName() != null && team.getShortName().toLowerCase().contains(lowerQuery))) {
                    results.add(new SearchResultDto(
                            "team",
                            team.getId(),
                            team.getName(),
                            team.getCountry()
                    ));
                }
            }
        }

        if (type == null || type.equals("all") || type.equals("match")) {
            List<Match> matches = matchRepository.findAll();
            for (Match match : matches) {
                if ((match.getTeam1Name() != null && match.getTeam1Name().toLowerCase().contains(lowerQuery)) ||
                    (match.getTeam2Name() != null && match.getTeam2Name().toLowerCase().contains(lowerQuery)) ||
                    (match.getVenue() != null && match.getVenue().toLowerCase().contains(lowerQuery))) {
                    results.add(new SearchResultDto(
                            "match",
                            match.getId(),
                            match.getTeam1Name() + " vs " + match.getTeam2Name(),
                            match.getVenue() + " - " + match.getStatus()
                    ));
                }
            }
        }

        return results;
    }

    public List<String> getCountries() {
        List<String> countries = new ArrayList<>();
        playerRepository.findAll().forEach(p -> {
            if (p.getCountry() != null && !countries.contains(p.getCountry())) {
                countries.add(p.getCountry());
            }
        });
        countries.sort(String::compareTo);
        return countries;
    }

    public List<String> getRoles() {
        return List.of("BATSMAN", "BOWLER", "ALL_ROUNDER", "WICKET_KEEPER");
    }

    public List<String> getVenues() {
        List<String> venues = new ArrayList<>();
        matchRepository.findAll().forEach(m -> {
            if (m.getVenue() != null && !venues.contains(m.getVenue())) {
                venues.add(m.getVenue());
            }
        });
        venues.sort(String::compareTo);
        return venues;
    }
}
