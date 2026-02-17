package com.crick.livecrick.entity;

import java.util.ArrayList;
import java.util.List;

public class Match {
    private Long id;
    private Long team1Id;
    private Long team2Id;
    private String team1Name;
    private String team2Name;
    private String matchType; // T20, ODI, TEST
    private String venue;
    private Long matchDate;
    private String status; // UPCOMING, LIVE, COMPLETED, ABANDONED
    private String tossWinner;
    private String tossDecision; // BAT, BOWL
    private String result;
    private Long winnerTeamId;
    private List<Innings> innings;
    private Long createdAt;
    private Long updatedAt;

    public Match() {
        this.innings = new ArrayList<>();
        this.createdAt = System.currentTimeMillis();
        this.updatedAt = System.currentTimeMillis();
        this.status = "UPCOMING";
    }

    public Match(Long id, Long team1Id, Long team2Id, String venue, Long matchDate) {
        this();
        this.id = id;
        this.team1Id = team1Id;
        this.team2Id = team2Id;
        this.venue = venue;
        this.matchDate = matchDate;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTeam1Id() {
        return team1Id;
    }

    public void setTeam1Id(Long team1Id) {
        this.team1Id = team1Id;
    }

    public Long getTeam2Id() {
        return team2Id;
    }

    public void setTeam2Id(Long team2Id) {
        this.team2Id = team2Id;
    }

    public String getTeam1Name() {
        return team1Name;
    }

    public void setTeam1Name(String team1Name) {
        this.team1Name = team1Name;
    }

    public String getTeam2Name() {
        return team2Name;
    }

    public void setTeam2Name(String team2Name) {
        this.team2Name = team2Name;
    }

    public String getMatchType() {
        return matchType;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public Long getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(Long matchDate) {
        this.matchDate = matchDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTossWinner() {
        return tossWinner;
    }

    public void setTossWinner(String tossWinner) {
        this.tossWinner = tossWinner;
    }

    public String getTossDecision() {
        return tossDecision;
    }

    public void setTossDecision(String tossDecision) {
        this.tossDecision = tossDecision;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Long getWinnerTeamId() {
        return winnerTeamId;
    }

    public void setWinnerTeamId(Long winnerTeamId) {
        this.winnerTeamId = winnerTeamId;
    }

    public List<Innings> getInnings() {
        return innings;
    }

    public void setInnings(List<Innings> innings) {
        this.innings = innings;
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

    public static class Innings {
        private int inningsNumber;
        private Long battingTeamId;
        private Long bowlingTeamId;
        private int runs;
        private int wickets;
        private double overs;
        private int extras;
        private List<Ball> balls;
        private List<BattingCard> battingCard;
        private List<BowlingCard> bowlingCard;
        private boolean completed;

        public Innings() {
            this.balls = new ArrayList<>();
            this.battingCard = new ArrayList<>();
            this.bowlingCard = new ArrayList<>();
            this.completed = false;
        }

        // Getters and Setters
        public int getInningsNumber() {
            return inningsNumber;
        }

        public void setInningsNumber(int inningsNumber) {
            this.inningsNumber = inningsNumber;
        }

        public Long getBattingTeamId() {
            return battingTeamId;
        }

        public void setBattingTeamId(Long battingTeamId) {
            this.battingTeamId = battingTeamId;
        }

        public Long getBowlingTeamId() {
            return bowlingTeamId;
        }

        public void setBowlingTeamId(Long bowlingTeamId) {
            this.bowlingTeamId = bowlingTeamId;
        }

        public int getRuns() {
            return runs;
        }

        public void setRuns(int runs) {
            this.runs = runs;
        }

        public int getWickets() {
            return wickets;
        }

        public void setWickets(int wickets) {
            this.wickets = wickets;
        }

        public double getOvers() {
            return overs;
        }

        public void setOvers(double overs) {
            this.overs = overs;
        }

        public int getExtras() {
            return extras;
        }

        public void setExtras(int extras) {
            this.extras = extras;
        }

        public List<Ball> getBalls() {
            return balls;
        }

        public void setBalls(List<Ball> balls) {
            this.balls = balls;
        }

        public List<BattingCard> getBattingCard() {
            return battingCard;
        }

        public void setBattingCard(List<BattingCard> battingCard) {
            this.battingCard = battingCard;
        }

        public List<BowlingCard> getBowlingCard() {
            return bowlingCard;
        }

        public void setBowlingCard(List<BowlingCard> bowlingCard) {
            this.bowlingCard = bowlingCard;
        }

        public boolean isCompleted() {
            return completed;
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }
    }

    public static class Ball {
        private int ballNumber;
        private Long batsmanId;
        private Long bowlerId;
        private int runs;
        private boolean isWicket;
        private String wicketType; // BOWLED, CAUGHT, LBW, RUN_OUT, etc.
        private String extraType; // WIDE, NO_BALL, BYE, LEG_BYE
        private int extraRuns;

        // Getters and Setters
        public int getBallNumber() {
            return ballNumber;
        }

        public void setBallNumber(int ballNumber) {
            this.ballNumber = ballNumber;
        }

        public Long getBatsmanId() {
            return batsmanId;
        }

        public void setBatsmanId(Long batsmanId) {
            this.batsmanId = batsmanId;
        }

        public Long getBowlerId() {
            return bowlerId;
        }

        public void setBowlerId(Long bowlerId) {
            this.bowlerId = bowlerId;
        }

        public int getRuns() {
            return runs;
        }

        public void setRuns(int runs) {
            this.runs = runs;
        }

        public boolean isWicket() {
            return isWicket;
        }

        public void setWicket(boolean wicket) {
            isWicket = wicket;
        }

        public String getWicketType() {
            return wicketType;
        }

        public void setWicketType(String wicketType) {
            this.wicketType = wicketType;
        }

        public String getExtraType() {
            return extraType;
        }

        public void setExtraType(String extraType) {
            this.extraType = extraType;
        }

        public int getExtraRuns() {
            return extraRuns;
        }

        public void setExtraRuns(int extraRuns) {
            this.extraRuns = extraRuns;
        }
    }

    public static class BattingCard {
        private Long playerId;
        private String playerName;
        private int runs;
        private int balls;
        private int fours;
        private int sixes;
        private double strikeRate;
        private String dismissal;
        private boolean isOnStrike;

        public BattingCard() {
        }

        public BattingCard(Long playerId, String playerName, boolean isOnStrike) {
            this.playerId = playerId;
            this.playerName = playerName;
            this.runs = 0;
            this.balls = 0;
            this.fours = 0;
            this.sixes = 0;
            this.strikeRate = 0.0;
            this.isOnStrike = isOnStrike;
        }

        // Getters and Setters
        public Long getPlayerId() {
            return playerId;
        }

        public void setPlayerId(Long playerId) {
            this.playerId = playerId;
        }

        public String getPlayerName() {
            return playerName;
        }

        public void setPlayerName(String playerName) {
            this.playerName = playerName;
        }

        public int getRuns() {
            return runs;
        }

        public void setRuns(int runs) {
            this.runs = runs;
        }

        public int getBalls() {
            return balls;
        }

        public void setBalls(int balls) {
            this.balls = balls;
        }

        public int getFours() {
            return fours;
        }

        public void setFours(int fours) {
            this.fours = fours;
        }

        public int getSixes() {
            return sixes;
        }

        public void setSixes(int sixes) {
            this.sixes = sixes;
        }

        public double getStrikeRate() {
            return strikeRate;
        }

        public void setStrikeRate(double strikeRate) {
            this.strikeRate = strikeRate;
        }

        public String getDismissal() {
            return dismissal;
        }

        public void setDismissal(String dismissal) {
            this.dismissal = dismissal;
        }

        public boolean isOnStrike() {
            return isOnStrike;
        }

        public void setOnStrike(boolean onStrike) {
            isOnStrike = onStrike;
        }
    }

    public static class BowlingCard {
        private Long playerId;
        private String playerName;
        private double overs;
        private int maidens;
        private int runs;
        private int wickets;
        private double economy;
        private int wides;
        private int noBalls;

        public BowlingCard() {
        }

        public BowlingCard(Long playerId, String playerName) {
            this.playerId = playerId;
            this.playerName = playerName;
            this.overs = 0.0;
            this.maidens = 0;
            this.runs = 0;
            this.wickets = 0;
            this.economy = 0.0;
            this.wides = 0;
            this.noBalls = 0;
        }

        // Getters and Setters
        public Long getPlayerId() {
            return playerId;
        }

        public void setPlayerId(Long playerId) {
            this.playerId = playerId;
        }

        public String getPlayerName() {
            return playerName;
        }

        public void setPlayerName(String playerName) {
            this.playerName = playerName;
        }

        public double getOvers() {
            return overs;
        }

        public void setOvers(double overs) {
            this.overs = overs;
        }

        public int getMaidens() {
            return maidens;
        }

        public void setMaidens(int maidens) {
            this.maidens = maidens;
        }

        public int getRuns() {
            return runs;
        }

        public void setRuns(int runs) {
            this.runs = runs;
        }

        public int getWickets() {
            return wickets;
        }

        public void setWickets(int wickets) {
            this.wickets = wickets;
        }

        public double getEconomy() {
            return economy;
        }

        public void setEconomy(double economy) {
            this.economy = economy;
        }

        public int getWides() {
            return wides;
        }

        public void setWides(int wides) {
            this.wides = wides;
        }

        public int getNoBalls() {
            return noBalls;
        }

        public void setNoBalls(int noBalls) {
            this.noBalls = noBalls;
        }
    }
}
