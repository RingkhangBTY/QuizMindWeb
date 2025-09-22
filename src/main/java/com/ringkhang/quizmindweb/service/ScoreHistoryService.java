package com.ringkhang.quizmindweb.service;

import com.ringkhang.quizmindweb.model.ScoreHistoryDisplay;
import com.ringkhang.quizmindweb.repo.ScoreHistoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScoreHistoryService {
    @Autowired
    private ScoreHistoryRepo repo;

    public List<ScoreHistoryDisplay> getScoreHistoriesByUserId(int id) {

        List<Object[]> result = repo.getScoreHistoriesByUserId(id);

        List<ScoreHistoryDisplay> historyDisplayList = new ArrayList<>();
        for (Object[] obj : result){
            String username = (String) obj[0];
            int totalQuestion = (Integer) obj[1];
            int correctAns = (Integer) obj[2];
            int testScore = (Integer) obj[3];
            String feedback = (String) obj[4];
            Timestamp timeStamp = (Timestamp) obj[5];

            LocalDateTime localDateTime = timeStamp != null ? timeStamp.toLocalDateTime():null;

            historyDisplayList.add(new ScoreHistoryDisplay(username,totalQuestion,correctAns,testScore,feedback,localDateTime));
        }

        return historyDisplayList;
    }
}