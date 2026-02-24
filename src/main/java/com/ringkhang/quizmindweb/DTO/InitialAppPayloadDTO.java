package com.ringkhang.quizmindweb.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter
@NoArgsConstructor @AllArgsConstructor
public class InitialAppPayloadDTO {
    private UserDetailsDTO userDetailsDTO;
    private ScoreHistoryDisplay scoreHistoryDisplay;
    private int totalQuizAttempts;
    private float avgScore;
    private int easyQuizCount;
    private int mediumQuizCount;
    private int hardQuizCount;
    private int hardPlusQuizCount;
}
