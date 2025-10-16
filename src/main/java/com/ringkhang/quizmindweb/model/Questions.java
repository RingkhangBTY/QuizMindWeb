package com.ringkhang.quizmindweb.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter @RequiredArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Questions {
    private String question;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String answer;
    private String explanation;
    private String userAnswer; // User's selected answer

    // Method to check if user answered correctly
//    public boolean isCorrect() {
//        return userAnswer != null && userAnswer.equals(answer);
//    }
}
