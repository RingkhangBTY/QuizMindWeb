package com.ringkhang.quizmindweb.DTO;

import com.ringkhang.quizmindweb.model.UserInput;
import lombok.Getter;

import java.util.List;

@Getter
public class SubmitQuizRequest {
    private List<Questions> questions;
    private UserInput userInput;
}