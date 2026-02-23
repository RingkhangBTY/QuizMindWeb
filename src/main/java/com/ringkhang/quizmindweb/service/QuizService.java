package com.ringkhang.quizmindweb.service;

import com.ringkhang.quizmindweb.DTO.Questions;
import com.ringkhang.quizmindweb.DTO.Result;
import com.ringkhang.quizmindweb.DTO.ScoreHistoryDisplay;
import com.ringkhang.quizmindweb.DTO.TestReviewDTO;
import com.ringkhang.quizmindweb.model.*;
import com.ringkhang.quizmindweb.repo.QuizRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {

    private final UsersDetailsService userDetailsService;
    private final ScoreHistoryService scoreHistoryService;
    private final QuizRepo quizRepo;
    private final MixedUtilService mixedUtilService;


    public QuizService(UsersDetailsService userDetailsService, ScoreHistoryService scoreHistoryService, QuizRepo quizRepo, MixedUtilService mixedUtilService) {
        this.userDetailsService = userDetailsService;
        this.scoreHistoryService = scoreHistoryService;
        this.quizRepo = quizRepo;
        this.mixedUtilService = mixedUtilService;
    }

    public Result calculateResult(List<Questions> questions) {
        int totalQuestions = questions.size();
        int correctAnswers = 0;

        for (Questions q : questions) {
            if (q.getUserAnswer().equals(q.getAnswer())) {
                correctAnswers++;
            }
        }

        int score = (int) ((correctAnswers / (double) totalQuestions) * 100);

        Result result = new Result();
        result.setTotalQuestions(totalQuestions);
        result.setCorrectAnswers(correctAnswers);
        result.setScore(score);

        if (score <=40){
            result.setComment("Need Improvement");
        } else if (score<=50) {
            result.setComment("Average");
        } else if (score<=60) {
            result.setComment("Not that bad");
        } else if (score<=70) {
            result.setComment("Good");
        } else if (score<=80) {
            result.setComment("Very Good");
        } else if (score<=100) {
            result.setComment("Excellent! Keep it up");
        }

        return result;
    }


    //
    public QuestionsTable getQuestionsTableEntity(Questions question, ScoreHistoryTable scoreHistoryTable) {

        QuestionsTable questionsTables = new QuestionsTable();

        questionsTables.setUserDetails(userDetailsService.getCurrentUserDetails());
        questionsTables.setQuestion(question.getQuestion());
        questionsTables.setOption_a(question.getOptionA());
        questionsTables.setOption_b(question.getOptionB());
        questionsTables.setOption_c(question.getOptionC());
        questionsTables.setOption_d(question.getOptionD());
        questionsTables.setCorrect_ans(question.getAnswer());
        questionsTables.setExplanation(question.getExplanation());
        questionsTables.setUser_ans(question.getUserAnswer());
        questionsTables.setScoreHistory(scoreHistoryTable);

        return questionsTables;
    }

    // calculates test result and save test result and questions to DB and return an object containing score and review data
    public ResponseEntity<TestReviewDTO> submitTest(List<Questions> questions, UserInput userInput) {

        //find test score
        Result result = calculateResult(questions);
        //save score-history table
        ScoreHistoryTable scoreHistoryTable = scoreHistoryService.saveHistory(
                questions,result,userInput);

        //save questions
        for (Questions question : questions) {
           QuestionsTable questionsTables = getQuestionsTableEntity(question, scoreHistoryTable);
            quizRepo.save(questionsTables);
        }

        //return
        return mixedUtilService.getTestReviewDetails(scoreHistoryTable.getScoreId());
    }
}