package com.crick.livecrick.entity;

public class Player {
    private Long id;
    private String name;
    private String photo;
    private String dateOfBirth;
    private String country;
    private String role; // BATSMAN, BOWLER, ALL_ROUNDER, WICKET_KEEPER
    private String battingStyle; // RIGHT_HAND, LEFT_HAND
    private String bowlingStyle; // FAST, MEDIUM, SPIN, etc.
    private Long teamId;
    private PlayerStatistics statistics;
    private Long createdAt;
    private Long updatedAt;

    public Player() {
        this.statistics = new PlayerStatistics();
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = System.currentTimeMillis();
    }

    public Player(Long id, String name, String country, String role) {
        this();
        this.id = id;
        this.name = name;
        this.country = country;
        this.role = role;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getBattingStyle() {
        return battingStyle;
    }

    public void setBattingStyle(String battingStyle) {
        this.battingStyle = battingStyle;
    }

    public String getBowlingStyle() {
        return bowlingStyle;
    }

    public void setBowlingStyle(String bowlingStyle) {
        this.bowlingStyle = bowlingStyle;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public PlayerStatistics getStatistics() {
        return statistics;
    }

    public void setStatistics(PlayerStatistics statistics) {
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

    public static class PlayerStatistics {
        private int matches = 0;
        private int innings = 0;
        private int runs = 0;
        private double average = 0.0;
        private double strikeRate = 0.0;
        private int highestScore = 0;
        private int hundreds = 0;
        private int fifties = 0;
        private int wickets = 0;
        private double bowlingAverage = 0.0;
        private double economy = 0.0;
        private String bestBowling = "";

        // Getters and Setters
        public int getMatches() {
            return matches;
        }

        public void setMatches(int matches) {
            this.matches = matches;
        }

        public int getInnings() {
            return innings;
        }

        public void setInnings(int innings) {
            this.innings = innings;
        }

        public int getRuns() {
            return runs;
        }

        public void setRuns(int runs) {
            this.runs = runs;
        }

        public double getAverage() {
            return average;
        }

        public void setAverage(double average) {
            this.average = average;
        }

        public double getStrikeRate() {
            return strikeRate;
        }

        public void setStrikeRate(double strikeRate) {
            this.strikeRate = strikeRate;
        }

        public int getHighestScore() {
            return highestScore;
        }

        public void setHighestScore(int highestScore) {
            this.highestScore = highestScore;
        }

        public int getHundreds() {
            return hundreds;
        }

        public void setHundreds(int hundreds) {
            this.hundreds = hundreds;
        }

        public int getFifties() {
            return fifties;
        }

        public void setFifties(int fifties) {
            this.fifties = fifties;
        }

        public int getWickets() {
            return wickets;
        }

        public void setWickets(int wickets) {
            this.wickets = wickets;
        }

        public double getBowlingAverage() {
            return bowlingAverage;
        }

        public void setBowlingAverage(double bowlingAverage) {
            this.bowlingAverage = bowlingAverage;
        }

        public double getEconomy() {
            return economy;
        }

        public void setEconomy(double economy) {
            this.economy = economy;
        }

        public String getBestBowling() {
            return bestBowling;
        }

        public void setBestBowling(String bestBowling) {
            this.bestBowling = bestBowling;
        }
    }
}
