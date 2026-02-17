package com.crick.livecrick.controller;

import com.crick.livecrick.dto.*;
import com.crick.livecrick.service.ScoreUpdateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/matches/{matchId}")
public class ScoreUpdateController {

    private final ScoreUpdateService scoreUpdateService;

    public ScoreUpdateController(ScoreUpdateService scoreUpdateService) {
        this.scoreUpdateService = scoreUpdateService;
    }

    @PostMapping("/innings")
    public ResponseEntity<MatchDto> startInnings(
            @PathVariable Long matchId,
            @RequestBody StartInningsRequest request) {
        return ResponseEntity.ok(scoreUpdateService.startInnings(matchId, request));
    }

    @PostMapping("/ball")
    public ResponseEntity<MatchDto> recordBall(
            @PathVariable Long matchId,
            @RequestBody RecordBallRequest request) {
        return ResponseEntity.ok(scoreUpdateService.recordBall(matchId, request));
    }

    @PostMapping("/wicket")
    public ResponseEntity<MatchDto> recordWicket(
            @PathVariable Long matchId,
            @RequestBody RecordWicketRequest request) {
        return ResponseEntity.ok(scoreUpdateService.recordWicket(matchId, request));
    }

    @PostMapping("/extra")
    public ResponseEntity<MatchDto> recordExtra(
            @PathVariable Long matchId,
            @RequestBody RecordExtraRequest request) {
        return ResponseEntity.ok(scoreUpdateService.recordExtra(matchId, request));
    }

    @PostMapping("/toss")
    public ResponseEntity<MatchDto> recordToss(
            @PathVariable Long matchId,
            @RequestBody RecordTossRequest request) {
        return ResponseEntity.ok(scoreUpdateService.recordToss(matchId, request));
    }
}
