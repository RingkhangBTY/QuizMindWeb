package com.ringkhang.quizmindweb.service;

import com.ringkhang.quizmindweb.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {
    @Autowired
    private UsersDetailsService userDetailsService;

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

    public QuestionsTable getQustionsTableEntity(Questions question, ScoreHistoryTable scoreHistoryTable) {

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
}