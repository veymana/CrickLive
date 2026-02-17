package com.crick.livecrick.service;

import com.crick.livecrick.dto.*;
import com.crick.livecrick.entity.Match;
import com.crick.livecrick.entity.Player;
import com.crick.livecrick.repository.MatchRepository;
import com.crick.livecrick.repository.PlayerRepository;
import com.crick.livecrick.websocket.LiveScoreWebSocketHandler;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;

@Service
public class ScoreUpdateService {

    private final MatchRepository matchRepository;
    private final PlayerRepository playerRepository;
    private final LiveScoreWebSocketHandler webSocketHandler;

    public ScoreUpdateService(MatchRepository matchRepository, PlayerRepository playerRepository, LiveScoreWebSocketHandler webSocketHandler) {
        this.matchRepository = matchRepository;
        this.playerRepository = playerRepository;
        this.webSocketHandler = webSocketHandler;
    }

    public MatchDto startInnings(Long matchId, StartInningsRequest request) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new RuntimeException("Match not found with id: " + matchId));

        Match.Innings innings = new Match.Innings();
        innings.setInningsNumber(request.inningsNumber());
        innings.setBattingTeamId(request.battingTeamId());
        innings.setBowlingTeamId(request.bowlingTeamId());
        innings.setBalls(new ArrayList<>());

        // Initialize batting card with opening batsmen
        if (request.openingBatsman1Id() != null && request.openingBatsman2Id() != null) {
            Player batsman1 = playerRepository.findById(request.openingBatsman1Id())
                    .orElseThrow(() -> new RuntimeException("Batsman 1 not found"));
            Player batsman2 = playerRepository.findById(request.openingBatsman2Id())
                    .orElseThrow(() -> new RuntimeException("Batsman 2 not found"));

            Match.BattingCard bc1 = new Match.BattingCard(batsman1.getId(), batsman1.getName(), true);
            Match.BattingCard bc2 = new Match.BattingCard(batsman2.getId(), batsman2.getName(), false);

            innings.getBattingCard().add(bc1);
            innings.getBattingCard().add(bc2);
        }

        // Initialize bowling card with opening bowler
        if (request.openingBowlerId() != null) {
            Player bowler = playerRepository.findById(request.openingBowlerId())
                    .orElseThrow(() -> new RuntimeException("Bowler not found"));

            Match.BowlingCard bowlingCard = new Match.BowlingCard(bowler.getId(), bowler.getName());
            innings.getBowlingCard().add(bowlingCard);
        }

        if (match.getInnings() == null) {
            match.setInnings(new ArrayList<>());
        }
        match.getInnings().add(innings);
        match.setStatus("LIVE");

        Match updated = matchRepository.save(match);
        broadcastScoreUpdate(updated, "INNINGS_STARTED");
        return MatchDto.from(updated);
    }

    public MatchDto recordBall(Long matchId, RecordBallRequest request) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new RuntimeException("Match not found with id: " + matchId));

        Match.Innings innings = findInnings(match, request.inningsNumber());

        Match.Ball ball = new Match.Ball();
        ball.setBallNumber(innings.getBalls().size() + 1);
        ball.setBatsmanId(request.batsmanId());
        ball.setBowlerId(request.bowlerId());
        ball.setRuns(request.runs());
        ball.setWicket(request.isWicket());
        ball.setWicketType(request.wicketType());
        ball.setExtraType(request.extraType());
        ball.setExtraRuns(request.extraRuns());

        innings.getBalls().add(ball);

        // Update innings totals
        innings.setRuns(innings.getRuns() + request.runs() + request.extraRuns());
        if (request.isWicket()) {
            innings.setWickets(innings.getWickets() + 1);
        }
        if (request.extraRuns() > 0) {
            innings.setExtras(innings.getExtras() + request.extraRuns());
        }

        // Update overs (assuming 6 valid balls per over)
        if (request.extraType() == null || request.extraType().isEmpty() || 
            (!request.extraType().equals("WIDE") && !request.extraType().equals("NO_BALL"))) {
            int validBalls = (int) ((innings.getOvers() * 10) % 10) + 1;
            if (validBalls == 6) {
                innings.setOvers(Math.floor(innings.getOvers()) + 1.0);
            } else {
                innings.setOvers(Math.floor(innings.getOvers()) + (validBalls / 10.0));
            }
        }

        Match updated = matchRepository.save(match);
        broadcastScoreUpdate(updated, "BALL_UPDATED");
        return MatchDto.from(updated);
    }

    public MatchDto recordWicket(Long matchId, RecordWicketRequest request) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new RuntimeException("Match not found with id: " + matchId));

        Match.Innings innings = findInnings(match, request.inningsNumber());
        innings.setWickets(innings.getWickets() + 1);

        Match updated = matchRepository.save(match);
        broadcastScoreUpdate(updated, "WICKET_RECORDED");
        return MatchDto.from(updated);
    }

    public MatchDto recordExtra(Long matchId, RecordExtraRequest request) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new RuntimeException("Match not found with id: " + matchId));

        Match.Innings innings = findInnings(match, request.inningsNumber());
        innings.setRuns(innings.getRuns() + request.runs());
        innings.setExtras(innings.getExtras() + request.runs());

        Match updated = matchRepository.save(match);
        broadcastScoreUpdate(updated, "EXTRA_RECORDED");
        return MatchDto.from(updated);
    }

    public MatchDto recordToss(Long matchId, RecordTossRequest request) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new RuntimeException("Match not found with id: " + matchId));

        match.setTossWinner(request.tossWinner());
        match.setTossDecision(request.tossDecision());

        Match updated = matchRepository.save(match);
        broadcastScoreUpdate(updated, "TOSS_RECORDED");
        return MatchDto.from(updated);
    }

    private Match.Innings findInnings(Match match, int inningsNumber) {
        if (match.getInnings() == null || match.getInnings().isEmpty()) {
            throw new RuntimeException("No innings found for match");
        }
        return match.getInnings().stream()
                .filter(i -> i.getInningsNumber() == inningsNumber)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Innings " + inningsNumber + " not found"));
    }

    /**
     * Broadcast score update to all WebSocket clients
     */
    private void broadcastScoreUpdate(Match match, String updateType) {
        try {
            Map<String, Object> message = Map.of(
                "type", updateType,
                "matchId", match.getId(),
                "match", MatchDto.from(match),
                "timestamp", System.currentTimeMillis()
            );
            webSocketHandler.broadcast(message);
        } catch (Exception e) {
            System.err.println("Error broadcasting score update: " + e.getMessage());
        }
    }
}
