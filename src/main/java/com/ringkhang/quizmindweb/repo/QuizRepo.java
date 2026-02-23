package com.ringkhang.quizmindweb.repo;

import com.ringkhang.quizmindweb.model.QuestionsTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepo extends JpaRepository<QuestionsTable,Integer> {


//    @Query(value = """
//        select q.q_id, sh.s_user_id, q.question, q.option_a, q.option_b,
//        q.option_c, q.option_d, q.correct_ans, q.explanation, q.user_ans
//        from questions q
//        join public.score_history sh on sh.score_id = q.q_history_id
//        where q.q_history_id = :h_id
//        """, nativeQuery = true)
//    List<QuizDetails> getQuestionsDetails(@Param("h_id") int h_id);


//    @Query("SELECT q FROM QuestionsTable q WHERE q.scoreHistory.scoreId = :scoreId")
//    List<QuestionsTable> findQuestionsByScoreId(@Param("scoreId") int scoreId);

//    List<Questions> getQuestionsWithUserDetails(@Param("his_id") int hisId);

//    @Transactional
//    @Modifying
//    @Query(value = """
//    INSERT INTO questions (
//        q_user_id, question, option_a, option_b, option_c, option_d,
//        correct_ans, explanation, user_ans, q_score_id
//    ) VALUES (
//        :user_id, :question, :option_a, :option_b, :option_c, :option_d,
//        :correct_ans, :explanation, :user_ans, :score_id
//    )
//""", nativeQuery = true)
//    void saveQuestions(@Param("user_id") int user_id,
//                       @Param("question") String question,
//                       @Param("option_a") String option_a,
//                       @Param("option_b") String option_b,
//                       @Param("option_c") String option_c,
//                       @Param("option_d") String option_d,
//                       @Param("correct_ans") String correct_ans,
//                       @Param("explanation") String explanation,
//                       @Param("user_ans") String user_ans,
//                       @Param("score_id") int score_id
//    );

//    @Query("""
//       SELECT q
//       FROM QuestionsTable q
//       WHERE q.scoreHistory.scoreId = :hId
//       """)
//    List<QuestionsTable> findByHistoryId(@Param("hId") int hId);

    @Query(value = """
   SELECT * FROM questions
   WHERE q_history_id = :hId
   """, nativeQuery = true)
    List<QuestionsTable> findByHistoryId(@Param("hId") int hId);

}