package com.example.pal.repository;

import com.example.pal.model.ExamAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExamAttemptRepository extends JpaRepository<ExamAttempt, Long>{
    @Query("SELECT * FROM ExamAttempt ea WHERE ea.user_id = :userId")
    List<ExamAttempt> findAllByUser(@Param("userId") Long userId);
}
