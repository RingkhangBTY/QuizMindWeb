package com.ringkhang.quizmindweb.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ringkhang.quizmindweb.model.Questions;
import com.ringkhang.quizmindweb.model.QuestionsTable;
import com.ringkhang.quizmindweb.model.ScoreHistoryTable;
import com.ringkhang.quizmindweb.model.UserInput;
import com.ringkhang.quizmindweb.service.GeminiService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class QuestionsController {

    @Autowired
    private GeminiService geminiService;

    @GetMapping("/start")
    public List<Questions> getResponse(@RequestBody UserInput input) throws JsonProcessingException {
        return geminiService.getQuestions(input);
    }

    @PostMapping("/submit_result")
    public ScoreHistoryTable sendResult() {
        return geminiService.evaluateResult();
    }
}