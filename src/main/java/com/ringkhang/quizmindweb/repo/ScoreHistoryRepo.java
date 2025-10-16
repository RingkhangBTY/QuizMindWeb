package com.ringkhang.quizmindweb.repo;

import com.ringkhang.quizmindweb.model.QuizDetails;
import com.ringkhang.quizmindweb.model.ScoreHistoryTable;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

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
}