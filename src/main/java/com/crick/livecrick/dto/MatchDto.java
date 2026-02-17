package com.crick.livecrick.dto;

import com.crick.livecrick.entity.Match;

import java.util.List;
import java.util.stream.Collectors;

public record MatchDto(
        Long id,
        Long team1Id,
        Long team2Id,
        String team1Name,
        String team2Name,
        String matchType,
        String venue,
        Long matchDate,
        String status,
        String tossWinner,
        String tossDecision,
        String result,
        Long winnerTeamId,
        List<InningsDto> innings,
        Long createdAt,
        Long updatedAt
) {
    public static MatchDto from(Match match) {
        return new MatchDto(
                match.getId(),
                match.getTeam1Id(),
                match.getTeam2Id(),
                match.getTeam1Name(),
                match.getTeam2Name(),
                match.getMatchType(),
                match.getVenue(),
                match.getMatchDate(),
                match.getStatus(),
                match.getTossWinner(),
                match.getTossDecision(),
                match.getResult(),
                match.getWinnerTeamId(),
                match.getInnings() != null ? 
                    match.getInnings().stream().map(InningsDto::from).collect(Collectors.toList()) : null,
                match.getCreatedAt(),
                match.getUpdatedAt()
        );
    }

    public record InningsDto(
            int inningsNumber,
            Long battingTeamId,
            Long bowlingTeamId,
            int runs,
            int wickets,
            double overs,
            int extras,
            boolean completed,
            List<BattingCardDto> battingCard,
            List<BowlingCardDto> bowlingCard
    ) {
        public static InningsDto from(Match.Innings innings) {
            return new InningsDto(
                    innings.getInningsNumber(),
                    innings.getBattingTeamId(),
                    innings.getBowlingTeamId(),
                    innings.getRuns(),
                    innings.getWickets(),
                    innings.getOvers(),
                    innings.getExtras(),
                    innings.isCompleted(),
                    innings.getBattingCard() != null ? 
                        innings.getBattingCard().stream().map(BattingCardDto::from).collect(Collectors.toList()) : null,
                    innings.getBowlingCard() != null ? 
                        innings.getBowlingCard().stream().map(BowlingCardDto::from).collect(Collectors.toList()) : null
            );
        }
    }

    public record BattingCardDto(
            Long playerId,
            String playerName,
            int runs,
            int balls,
            int fours,
            int sixes,
            double strikeRate,
            String dismissal,
            boolean isOnStrike
    ) {
        public static BattingCardDto from(Match.BattingCard card) {
            return new BattingCardDto(
                    card.getPlayerId(),
                    card.getPlayerName(),
                    card.getRuns(),
                    card.getBalls(),
                    card.getFours(),
                    card.getSixes(),
                    card.getStrikeRate(),
                    card.getDismissal(),
                    card.isOnStrike()
            );
        }
    }

    public record BowlingCardDto(
            Long playerId,
            String playerName,
            double overs,
            int maidens,
            int runs,
            int wickets,
            double economy,
            int wides,
            int noBalls
    ) {
        public static BowlingCardDto from(Match.BowlingCard card) {
            return new BowlingCardDto(
                    card.getPlayerId(),
                    card.getPlayerName(),
                    card.getOvers(),
                    card.getMaidens(),
                    card.getRuns(),
                    card.getWickets(),
                    card.getEconomy(),
                    card.getWides(),
                    card.getNoBalls()
            );
        }
    }
}
