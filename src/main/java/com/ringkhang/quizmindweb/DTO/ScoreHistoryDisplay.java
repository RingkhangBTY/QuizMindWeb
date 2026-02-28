package com.ringkhang.quizmindweb.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScoreHistoryDisplay {

    private int quizHistoryId;
    private String username;
    private int total_question;
    private int correct_ans;
    private int test_score;
    private String feedback;
    private String topic_sub;
    private  String level;
    private  String short_des;
    private LocalDateTime time_stamp;
}