package com.ringkhang.quizmindweb.DTO;

import com.ringkhang.quizmindweb.model.UserInput;

import java.util.List;

public class SubmitQuizRequest {
    private List<Questions> questions;
    private UserInput userInput;

    public List<Questions> getQuestions() { return questions; }
    public UserInput getUserInput() { return userInput; }
}