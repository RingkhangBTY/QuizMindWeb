package com.ringkhang.quizmindweb.controller;

import com.ringkhang.quizmindweb.model.QuizDetails;
import com.ringkhang.quizmindweb.model.ScoreHistoryDisplay;
import com.ringkhang.quizmindweb.service.ScoreHistoryService;
import com.ringkhang.quizmindweb.service.UsersDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ScoreHistoryController {

    @Autowired
    private ScoreHistoryService service;
    @Autowired
    private UsersDetailsService usersDetailsService;

    @GetMapping("/scoreHistory")
    public List<ScoreHistoryDisplay> getCurrentUserHistory(){
        return service.getScoreHistoriesByUserId(usersDetailsService.getCurrentUserId());
    }
}