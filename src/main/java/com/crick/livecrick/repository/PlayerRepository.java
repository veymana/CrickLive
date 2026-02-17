package com.crick.livecrick.repository;

import com.crick.livecrick.entity.Player;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class PlayerRepository {
    private final Map<Long, Player> players = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Player save(Player player) {
        if (player.getId() == null) {
            player.setId(idGenerator.getAndIncrement());
            player.setCreatedAt(System.currentTimeMillis());
        }
        player.setUpdatedAt(System.currentTimeMillis());
        players.put(player.getId(), player);
        return player;
    }

    public Optional<Player> findById(Long id) {
        return Optional.ofNullable(players.get(id));
    }

    public List<Player> findAll() {
        return new ArrayList<>(players.values());
    }

    public List<Player> findByTeamId(Long teamId) {
        return players.values().stream()
                .filter(player -> teamId.equals(player.getTeamId()))
                .collect(Collectors.toList());
    }

    public List<Player> findByCountry(String country) {
        return players.values().stream()
                .filter(player -> country.equalsIgnoreCase(player.getCountry()))
                .collect(Collectors.toList());
    }

    public List<Player> findByRole(String role) {
        return players.values().stream()
                .filter(player -> role.equalsIgnoreCase(player.getRole()))
                .collect(Collectors.toList());
    }

    public List<Player> searchByName(String name) {
        String lowerName = name.toLowerCase();
        return players.values().stream()
                .filter(player -> player.getName().toLowerCase().contains(lowerName))
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        players.remove(id);
    }

    public boolean existsById(Long id) {
        return players.containsKey(id);
    }

    public long count() {
        return players.size();
    }
}
