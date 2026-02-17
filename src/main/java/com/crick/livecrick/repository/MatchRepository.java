package com.crick.livecrick.repository;

import com.crick.livecrick.entity.Match;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class MatchRepository {
    private final Map<Long, Match> matches = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Match save(Match match) {
        if (match.getId() == null) {
            match.setId(idGenerator.getAndIncrement());
            match.setCreatedAt(System.currentTimeMillis());
        }
        match.setUpdatedAt(System.currentTimeMillis());
        matches.put(match.getId(), match);
        return match;
    }

    public Optional<Match> findById(Long id) {
        return Optional.ofNullable(matches.get(id));
    }

    public List<Match> findAll() {
        return new ArrayList<>(matches.values());
    }

    public List<Match> findByStatus(String status) {
        return matches.values().stream()
                .filter(match -> status.equalsIgnoreCase(match.getStatus()))
                .collect(Collectors.toList());
    }

    public List<Match> findByTeamId(Long teamId) {
        return matches.values().stream()
                .filter(match -> teamId.equals(match.getTeam1Id()) || teamId.equals(match.getTeam2Id()))
                .collect(Collectors.toList());
    }

    public List<Match> findLiveMatches() {
        return findByStatus("LIVE");
    }

    public List<Match> findUpcomingMatches() {
        return matches.values().stream()
                .filter(match -> "UPCOMING".equalsIgnoreCase(match.getStatus()))
                .sorted(Comparator.comparing(Match::getMatchDate))
                .collect(Collectors.toList());
    }

    public List<Match> findCompletedMatches() {
        return matches.values().stream()
                .filter(match -> "COMPLETED".equalsIgnoreCase(match.getStatus()))
                .sorted(Comparator.comparing(Match::getMatchDate).reversed())
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        matches.remove(id);
    }

    public boolean existsById(Long id) {
        return matches.containsKey(id);
    }

    public long count() {
        return matches.size();
    }
}
