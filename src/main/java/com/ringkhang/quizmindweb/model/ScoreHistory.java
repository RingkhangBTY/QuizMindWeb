package com.ringkhang.quizmindweb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity @Table(name = "score_history")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ScoreHistory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int score_id;
    //🔹 Foreign Key -> user_details(id)
    @ManyToOne
    @JoinColumn(name = "s_user_id", nullable = false)
    private UserDetails userDetails;

    private int total_question;
    private int correct_ans;
    private int test_score;
    private String feedback;

    @Column(name = "time_stamp",nullable = false,updatable = false)
    @org.hibernate.annotations.CreationTimestamp
    private LocalDateTime time_stamp;
}
