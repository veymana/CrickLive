package com.crick.livecrick.repository;

import com.crick.livecrick.entity.Team;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class TeamRepository {
    private final Map<Long, Team> teams = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Team save(Team team) {
        if (team.getId() == null) {
            team.setId(idGenerator.getAndIncrement());
            team.setCreatedAt(System.currentTimeMillis());
        }
        team.setUpdatedAt(System.currentTimeMillis());
        teams.put(team.getId(), team);
        return team;
    }

    public Optional<Team> findById(Long id) {
        return Optional.ofNullable(teams.get(id));
    }

    public List<Team> findAll() {
        return new ArrayList<>(teams.values());
    }

    public List<Team> findByCountry(String country) {
        return teams.values().stream()
                .filter(team -> country.equalsIgnoreCase(team.getCountry()))
                .collect(Collectors.toList());
    }

    public Optional<Team> findByName(String name) {
        return teams.values().stream()
                .filter(team -> name.equalsIgnoreCase(team.getName()))
                .findFirst();
    }

    public void deleteById(Long id) {
        teams.remove(id);
    }

    public boolean existsById(Long id) {
        return teams.containsKey(id);
    }

    public long count() {
        return teams.size();
    }
}
