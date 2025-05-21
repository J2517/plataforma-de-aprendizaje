package com.example.pal.repository;

import com.example.pal.model.ExamAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ExamAttemptRepository extends JpaRepository<ExamAttempt, Long> {

    @Query("SELECT ea FROM ExamAttempt ea WHERE ea.student.id = :studentId")
    List<ExamAttempt> findAllByStudentId(@Param("studentId") Long studentId);

    Optional<ExamAttempt> findTopByExamIdAndStudentIdOrderByScoreDesc(Long examId, Long studentId);

}
