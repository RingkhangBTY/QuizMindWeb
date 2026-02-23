package com.ringkhang.quizmindweb.service;

import com.ringkhang.quizmindweb.DTO.Questions;
import com.ringkhang.quizmindweb.DTO.ScoreHistoryDisplay;
import com.ringkhang.quizmindweb.DTO.TestReviewDTO;
import com.ringkhang.quizmindweb.DTO.QuizDetails;
import com.ringkhang.quizmindweb.model.QuestionsTable;
import com.ringkhang.quizmindweb.repo.QuizRepo;
import com.ringkhang.quizmindweb.repo.ScoreHistoryRepo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MixedUtilService {
    private final QuizRepo quizRepo;
    private final ScoreHistoryRepo scoreHistoryRepo;
    private final MyUserDetailsService userDetailsService;
    private final ScoreHistoryService scoreHistoryService;

    public MixedUtilService(QuizRepo quizRepo,ScoreHistoryRepo scoreHistoryRepo, MyUserDetailsService userDetailsService, ScoreHistoryService scoreHistoryService) {
        this.quizRepo = quizRepo;
        this.scoreHistoryRepo = scoreHistoryRepo;
        this.userDetailsService = userDetailsService;
        this.scoreHistoryService = scoreHistoryService;
    }

    public ResponseEntity<TestReviewDTO> getTestReviewDetails(int testHisId) {
        ScoreHistoryDisplay scoreHistoryDisplay = scoreHistoryService.getTestScoreHistoryByHisId(testHisId);
        List<QuestionsTable> questionsTables = quizRepo.findByHistoryId(testHisId);

        List<Questions> questionsList = new ArrayList<>();
        for (QuestionsTable qt : questionsTables){
            questionsList.add(new Questions(
                  qt.getQuestion(),
                  qt.getOption_a(),
                  qt.getOption_b(),
                  qt.getOption_c(),
                  qt.getOption_d(),
                  qt.getCorrect_ans(),
                  qt.getExplanation(),
                  qt.getUser_ans()
            ));
        }

        TestReviewDTO testReviewDTO = new TestReviewDTO(
                scoreHistoryDisplay,questionsList

        );

        return ResponseEntity.status(HttpStatus.OK)
                .body(testReviewDTO);
    }
}