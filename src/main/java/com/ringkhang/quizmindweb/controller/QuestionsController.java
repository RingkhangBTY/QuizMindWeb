package com.ringkhang.quizmindweb.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ringkhang.quizmindweb.DTO.QuizDetails;
import com.ringkhang.quizmindweb.DTO.SubmitQuizRequest;
import com.ringkhang.quizmindweb.DTO.TestReviewDTO;
import com.ringkhang.quizmindweb.model.UserInput;
import com.ringkhang.quizmindweb.repo.QuizRepo;
import com.ringkhang.quizmindweb.service.GeminiService;
import com.ringkhang.quizmindweb.service.QuizService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/quiz")
public class QuestionsController {

    private final QuizRepo quizRepo;
    private final GeminiService geminiService;
    private final QuizService quizService;

    public QuestionsController(QuizRepo quizRepo,
                               GeminiService geminiService,
                               QuizService quizService) {
        this.quizRepo = quizRepo;
        this.geminiService = geminiService;
        this.quizService = quizService;
    }

    @PostMapping("/start")
    public List<QuizDetails> startQuiz(@RequestBody UserInput input) throws JsonProcessingException {

        Logger.getLogger(QuestionsController.class.getName())
                .log(Level.INFO,"Requested questions for test....");

        return geminiService.getQuestions(input);
    }

    @PostMapping("/submit")
    public ResponseEntity<TestReviewDTO> submitQuiz(@RequestBody SubmitQuizRequest request) {
        Logger.getLogger(QuestionsController.class.getName())
                .log(Level.INFO,"Submitted the test and requested for test result/review");

        return quizService.submitTest(request.getQuestions(), request.getUserInput());
    }
}