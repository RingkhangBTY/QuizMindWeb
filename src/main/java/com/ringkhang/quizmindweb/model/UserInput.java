package com.ringkhang.quizmindweb.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// To store user input for question generation
public class UserInput {
    private String programmingLanguage_Subject;
    private String shortDes_Topic_Concepts;
    private String level;
    private int noOfQ;
}