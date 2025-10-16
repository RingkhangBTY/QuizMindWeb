package com.ringkhang.quizmindweb.service;

import com.ringkhang.quizmindweb.model.*;
import com.ringkhang.quizmindweb.repo.QuizRepo;
import com.ringkhang.quizmindweb.repo.ScoreHistoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ScoreHistoryService {
    @Autowired
    private ScoreHistoryRepo historyRepo;
    @Autowired
    private UsersDetailsService usersDetailsService;
    @Autowired
    private QuizRepo quizRepo;

    public ScoreHistoryTable saveHistory(List<Questions> questions , Result result, UserInput userInput) {

        ScoreHistoryTable scoreHistoryTable = new ScoreHistoryTable();

        scoreHistoryTable.setUserDetails(usersDetailsService.getCurrentUserDetails());
        scoreHistoryTable.setTotal_question(result.getTotalQuestions());
        scoreHistoryTable.setCorrect_ans(result.getCorrectAnswers());
        scoreHistoryTable.setTest_score(result.getScore());
        scoreHistoryTable.setFeedback(result.getComment());
        scoreHistoryTable.setTopic_sub(userInput.getProgrammingLanguage_Subject());
        scoreHistoryTable.setLevel(userInput.getLevel());
        scoreHistoryTable.setShort_des(userInput.getShortDes_Topic_Concepts());

        return historyRepo.save(scoreHistoryTable);
    }

    public List<ScoreHistoryDisplay> getScoreHistoriesByUserId(int id) {
        List<ScoreHistoryTable> scoreHistories = historyRepo.getScoreHistoriesByUserId(id);

        List<ScoreHistoryDisplay> historyDisplayList = new ArrayList<>();
        for (ScoreHistoryTable sh : scoreHistories) {
            String username = sh.getUserDetails().getUsername();
            int totalQuestion = sh.getTotal_question();
            int correctAns = sh.getCorrect_ans();
            int testScore = sh.getTest_score();
            String feedback = sh.getFeedback(); // sh.getFeedback() if you have this field
            String topicSub = sh.getTopic_sub(); // Placeholder, set appropriately if available
            String level = sh.getLevel(); // Placeholder, set appropriately if available
            String shortDes = sh.getShort_des(); // Placeholder, set appropriately if available
            LocalDateTime timeStamp = sh.getTime_stamp();

            historyDisplayList.add(new ScoreHistoryDisplay(username, totalQuestion, correctAns, testScore, feedback,topicSub,level,shortDes,timeStamp));
        }
        return historyDisplayList;
    }

    public List<QuizDetails> getQuizDetailsById(int scoreHistoryId) {
        return quizRepo.getQuestionsDetails(scoreHistoryId);
    }

    public void save(ScoreHistoryTable scoreHistory) {
        historyRepo.save(scoreHistory);
    }

    // Get the most recent score history ID for a user
    public int getLatestScoreHistoryId(int userId) {
        List<ScoreHistoryTable> scoreHistories = historyRepo.getScoreHistoriesByUserId(userId);
        if (scoreHistories.isEmpty()) {
            throw new IllegalStateException("No quiz history found for user");
        }

        // Get the most recent one (assuming they're ordered by creation time)
        // If not ordered, you might need to sort by time_stamp
        ScoreHistoryTable latest = scoreHistories.stream()
                .max((sh1, sh2) -> sh1.getTime_stamp().compareTo(sh2.getTime_stamp()))
                .orElseThrow(() -> new IllegalStateException("No quiz history found"));

        return latest.getScore_id();
    }

    // Get score history ID by index (0 = most recent, 1 = second most recent, etc.)
    public int getScoreHistoryIdByIndex(int userId, int index) {
        List<ScoreHistoryTable> scoreHistories = historyRepo.getScoreHistoriesByUserId(userId);
        if (scoreHistories.isEmpty() || index >= scoreHistories.size()) {
            throw new IllegalStateException("Quiz not found at index " + index);
        }

        // Sort by timestamp in descending order (most recent first)
        scoreHistories.sort((sh1, sh2) -> sh2.getTime_stamp().compareTo(sh1.getTime_stamp()));

        return scoreHistories.get(index).getScore_id();
    }
}