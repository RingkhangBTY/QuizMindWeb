package com.ringkhang.quizmindweb.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter @RequiredArgsConstructor
@AllArgsConstructor
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

    //Custom getter to ensure never null
    public String getUserAnswer() {
        return (userAnswer != null && !userAnswer.trim().isEmpty())
                ? userAnswer
                : "Not Answered";
    }

    //Custom setter to handle null
    public void setUserAnswer(String user_ans) {
        this.userAnswer = (user_ans != null && !user_ans.trim().isEmpty())
                ? user_ans
                : "Not Answered";
    }
}
