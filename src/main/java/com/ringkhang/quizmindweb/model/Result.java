package com.ringkhang.quizmindweb.model;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
public class Result {
    private int TotalQuestions;
    private int CorrectAnswers;
    private int Score;
    private String comment;
}
