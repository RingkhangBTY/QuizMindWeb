package com.ringkhang.quizmindweb.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScoreHistoryDisplay {

    private String username;
    private int total_question;
    private int correct_ans;
    private int test_score;
    private String feedback;
    private LocalDateTime time_stamp;

//    public ScoreHistoryDisplay(String username, int correctAns, int testScore, String feedback, Timestamp timeStamp) {
//    }
}
