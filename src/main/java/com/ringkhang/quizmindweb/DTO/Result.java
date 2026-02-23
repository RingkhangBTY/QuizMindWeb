package com.ringkhang.quizmindweb.DTO;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
public class Result {
    private int TotalQuestions;
    private int CorrectAnswers;
    private int Score;
    private String comment;
}