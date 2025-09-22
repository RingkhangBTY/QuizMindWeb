package com.ringkhang.quizmindweb.repo;

import com.ringkhang.quizmindweb.model.ScoreHistoryDisplay;
import com.ringkhang.quizmindweb.model.ScoreHistoryTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreHistoryRepo extends JpaRepository<ScoreHistoryTable,Integer> {

    @Query(value = "select u.username, sh.total_question , sh.correct_ans, sh.test_score,\n" +
            "       sh.feedback,sh.time_stamp\n" +
            "from user_details u\n" +
            "join score_history sh on u.id = sh.s_user_id\n" +
            "where u.id = :userId;",
            nativeQuery = true)
    List<Object[]> getScoreHistoriesByUserId(@Param("userId") int id);
}