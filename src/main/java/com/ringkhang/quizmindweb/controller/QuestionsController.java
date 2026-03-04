package com.ringkhang.quizmindweb.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ringkhang.quizmindweb.DTO.QuizDetails;
import com.ringkhang.quizmindweb.DTO.ReTestSubmitRequest;
import com.ringkhang.quizmindweb.DTO.SubmitQuizRequest;
import com.ringkhang.quizmindweb.DTO.TestReviewDTO;
import com.ringkhang.quizmindweb.model.UserInput;
import com.ringkhang.quizmindweb.repo.QuizRepo;
import com.ringkhang.quizmindweb.service.GeminiService;
import com.ringkhang.quizmindweb.service.QuizService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
@Slf4j
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
        log.info("Requested questions for test....");
        return geminiService.getQuestions(input);
    }

    @PostMapping("/submit")
    public ResponseEntity<TestReviewDTO> submitQuiz(@RequestBody SubmitQuizRequest request) {
        log.info("Submitted the test and requested for test result/review");
        return quizService.submitTest(request.getQuestions(), request.getUserInput());
    }

    @PostMapping("/submit/re-test")
    public ResponseEntity<TestReviewDTO> submitReTest(@RequestBody ReTestSubmitRequest request){
        log.info("Submit request for re test...");
        return quizService.submitReTest(request);
    }
}