package com.example.pal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.pal.model.Course;
import com.example.pal.model.Enrollment;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {

    @Query("SELECT COUNT(e) > 0 FROM Enrollment e WHERE e.user.id = :userId AND e.course.id = :courseId")
    boolean existsByUserInCourse(@Param("userId") Long userId, @Param("courseId") Long courseId);

    @Query("SELECT e.course FROM Enrollment e WHERE e.user.id = :userId")
    List<Course> findCoursesByUser(@Param("userId") Long userId);

}
