package com.ringkhang.quizmindweb.repo;

import com.ringkhang.quizmindweb.DTO.QuizDetails;
import com.ringkhang.quizmindweb.model.ScoreHistoryTable;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScoreHistoryRepo extends JpaRepository<ScoreHistoryTable,Integer> {

    @Query("SELECT sh FROM ScoreHistoryTable sh WHERE sh.userDetails.id = :id")
    List<ScoreHistoryTable> getScoreHistoriesByUserId(@Param("id") int id);


    @Transactional
    @Modifying
    @Query(value = """
            INSERT INTO public.score_history (
                                        s_user_id, total_question, correct_ans, test_score
                                    ) VALUES (
                                              :currentUserId, :totalQuestions, :correctAnswers, :score
            ) RETURNING score_id;
    """, nativeQuery = true)
    ScoreHistoryTable saveHistory(
            @Param("currentUserId") int currentUserId,
            @Param("totalQuestions") int totalQuestions,
            @Param("correctAnswers") int correctAnswers,
            @Param("score") int score
    );

    @Query(value = """
                    SELECT q.question, q.option_a AS optionA, q.option_b AS optionB, q.option_c AS optionC, q.option_d AS optionD,
                           q.correct_ans AS answer, q.explanation
                    FROM questions q
                    JOIN score_history sh ON q.Q_history_id = sh.score_id
                    WHERE sh.time_stamp = :timestamp
            """, nativeQuery = true)
    List<QuizDetails> getQuizDetailsByTimestamp(@Param("timestamp") LocalDateTime timestamp);

    //gives history details by hisId
    @Query(value = "SELECT sh FROM ScoreHistoryTable sh where sh.scoreId = :hisId")
    ScoreHistoryTable getScoreHistoryTableByHisId(@Param("hisId") int hisId);

}