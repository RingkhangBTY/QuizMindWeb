package com.ringkhang.quizmindweb.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ringkhang.quizmindweb.model.*;
import com.ringkhang.quizmindweb.repo.QuizRepo;
import com.ringkhang.quizmindweb.service.GeminiService;
import com.ringkhang.quizmindweb.service.QuizService;
import com.ringkhang.quizmindweb.service.ScoreHistoryService;
import com.ringkhang.quizmindweb.service.UsersDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
//@RequiredArgsConstructor
//@RequestMapping("/quiz")
public class QuestionsController {

    @Autowired
    private QuizRepo quizRepo;
    @Autowired
    private GeminiService geminiService;
    @Autowired
    private QuizService quizService;
    @Autowired
    private  UsersDetailsService userDetailsService;
    @Autowired
    private ScoreHistoryService scoreHistoryService;

    private UserInput userInput;

    @PostMapping("/start")
    public List<QuizDetails> startQuiz(@RequestBody UserInput input) throws JsonProcessingException {
        userInput = input;
        List<QuizDetails> questions = geminiService.getQuestions(input);
        return questions;
    }

    @PostMapping("/submit")
    public Result submitQuiz(@RequestBody List<Questions> questions) throws JsonProcessingException {
        Result result = quizService.calculateResult(questions);
        ScoreHistoryTable scoreHistoryTable = scoreHistoryService.saveHistory(questions,result,userInput);

        for (Questions question : questions) {
            QuestionsTable questionsTables = quizService.getQustionsTableEntity(question, scoreHistoryTable);
            quizRepo.save(questionsTables);
        }
        return result;
    }
}