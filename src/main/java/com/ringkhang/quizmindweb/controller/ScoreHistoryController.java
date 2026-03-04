package com.ringkhang.quizmindweb.controller;

import com.ringkhang.quizmindweb.DTO.QuizDetails;
import com.ringkhang.quizmindweb.DTO.ScoreHistoryDisplay;
import com.ringkhang.quizmindweb.DTO.TestReviewDTO;
import com.ringkhang.quizmindweb.service.MixedUtilService;
import com.ringkhang.quizmindweb.service.ScoreHistoryService;
import com.ringkhang.quizmindweb.service.UsersDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/history")
@Slf4j
public class ScoreHistoryController {

    private final ScoreHistoryService scoreHistoryService;
    private final UsersDetailsService usersDetailsService;
    private final MixedUtilService mixedUtilService;

    public ScoreHistoryController(ScoreHistoryService scoreHistoryService,
                                  UsersDetailsService usersDetailsService,
                                  MixedUtilService mixedUtilService) {
        this.scoreHistoryService = scoreHistoryService;
        this.usersDetailsService = usersDetailsService;
        this.mixedUtilService = mixedUtilService;
    }

    @GetMapping("/scoreHistory")
    public List<ScoreHistoryDisplay> getCurrentUserHistory() {
        log.info("Requested test score history data...");

        return scoreHistoryService.getScoreHistoriesByUserId(usersDetailsService.getCurrentUserId());
    }

    @GetMapping("/testAgain")
    public ResponseEntity<List<QuizDetails>> testAgain(@RequestParam int testHisId) {
        log.info("Requested for re-test...");

        return scoreHistoryService.getQuizDetailsById(testHisId);
    }

    @GetMapping("/review_test")
    public ResponseEntity<TestReviewDTO> reviewTestResults(@RequestParam int testHisId) {
        log.info("Requested for test review...");

        return mixedUtilService.getTestReviewDetails(testHisId);
    }
}