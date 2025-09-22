package com.ringkhang.quizmindweb.controller;

import com.ringkhang.quizmindweb.model.ScoreHistoryDisplay;
import com.ringkhang.quizmindweb.service.ScoreHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ScoreHistoryController {

    @Autowired
    private ScoreHistoryService service;

    @GetMapping("/scoreHistory/{userId}")
    public List<ScoreHistoryDisplay> getHistory(@PathVariable("userId") int id){
        return service.getScoreHistoriesByUserId(id);
    }

}
