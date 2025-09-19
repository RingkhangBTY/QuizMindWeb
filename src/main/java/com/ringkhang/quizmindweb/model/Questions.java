package com.ringkhang.quizmindweb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity @Table(name = "questions")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Questions {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int q_id;

    // 🔹 Instead of raw int, map to entities
    @ManyToOne
    @JoinColumn(name = "q_score_id", nullable = false)  // FK -> score_history(score_id)
    private ScoreHistory scoreHistory; // Foreign key references score_history tables id

    @ManyToOne
    @JoinColumn(name = "q_user_id", nullable = false)   // FK -> user_details(id)
    private UserDetails userDetails; // Foreign key references user_details tables id

    private String question;

    private String option_a;
    private String option_b;
    private String option_c;
    private String option_d;

    private String correct_ans;
    private String explanation;
    private String user_ans;


    @Column(name = "time_stamp",nullable = false,updatable = false)
    @org.hibernate.annotations.CreationTimestamp
    private LocalDateTime time_stamp;
}