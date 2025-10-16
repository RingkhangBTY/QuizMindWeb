package com.ringkhang.quizmindweb.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity @Table(name = "score_history")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ScoreHistoryTable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int score_id;

    //🔹 Foreign Key -> user_details(id)
    @ManyToOne
    @JoinColumn(name = "s_user_id", nullable = false)
    private UserDetailsTable userDetails;

    private int total_question;
    private int correct_ans;
    private int test_score;
//    private String feedback;

    @Column(name = "time_stamp",nullable = false,updatable = false)
    @org.hibernate.annotations.CreationTimestamp
    private LocalDateTime time_stamp;
    private String feedback;
    private String topic_sub;
    private String level;
    private String short_des;

    // A score/history can be associated with multiple questions; use a join table
//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//        name = "score_history_questions",
//        joinColumns = @JoinColumn(name = "score_id"),
//        inverseJoinColumns = @JoinColumn(name = "question_id")
//    )
//    private Set<QuestionsTable> questions = new HashSet<>();

}