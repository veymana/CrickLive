package com.crick.livecrick.entity;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private Long id;
    private String name;
    private String shortName;
    private String logo;
    private String country;
    private List<Long> playerIds;
    private TeamStatistics statistics;
    private Long createdAt;
    private Long updatedAt;

    public Team() {
        this.playerIds = new ArrayList<>();
        this.statistics = new TeamStatistics();
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = System.currentTimeMillis();
    }

    public Team(Long id, String name, String shortName, String country) {
        this();
        this.id = id;
        this.name = name;
        this.shortName = shortName;
        this.country = country;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<Long> getPlayerIds() {
        return playerIds;
    }

    public void setPlayerIds(List<Long> playerIds) {
        this.playerIds = playerIds;
    }

    public TeamStatistics getStatistics() {
        return statistics;
    }

    public void setStatistics(TeamStatistics statistics) {
        this.statistics = statistics;
    }

    public Long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Long createdAt) {
        this.createdAt = createdAt;
    }

    public Long getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Long updatedAt) {
        this.updatedAt = updatedAt;
    }

    public static class TeamStatistics {
        private int matchesPlayed = 0;
        private int won = 0;
        private int lost = 0;
        private int tied = 0;
        private int noResult = 0;

        // Getters and Setters
        public int getMatchesPlayed() {
            return matchesPlayed;
        }

        public void setMatchesPlayed(int matchesPlayed) {
            this.matchesPlayed = matchesPlayed;
        }

        public int getWon() {
            return won;
        }

        public void setWon(int won) {
            this.won = won;
        }

        public int getLost() {
            return lost;
        }

        public void setLost(int lost) {
            this.lost = lost;
        }

        public int getTied() {
            return tied;
        }

        public void setTied(int tied) {
            this.tied = tied;
        }

        public int getNoResult() {
            return noResult;
        }

        public void setNoResult(int noResult) {
            this.noResult = noResult;
        }
    }
}
