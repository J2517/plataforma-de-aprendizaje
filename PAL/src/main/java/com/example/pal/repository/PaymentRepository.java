package com.example.pal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.pal.model.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    @Query("SELECT COUNT(p) > 0 FROM Payment p WHERE p.user.id = :userId AND p.course.id = :courseId")
    boolean hasUserPaidForCourse(@Param("userId") Long userId, @Param("courseId") Long courseId);
}
