package com.example.pal.repository;

import com.example.pal.model.ExamAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ExamAttemptRepository extends JpaRepository<ExamAttempt, Long>{
    @Query("SELECT * FROM ExamAttempt ea WHERE ea.user_id = :userId")
    List<ExamAttempt> findAllByUser(@Param("userId") Long userId);

    Optional<ExamAttempt> findTopByExamIdAndStudentIdOrderByScoreDesc(Long examId, Long studentId);

}
