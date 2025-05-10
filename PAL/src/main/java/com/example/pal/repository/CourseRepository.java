package com.example.pal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.pal.model.Course;

@Repository
public interface  CourseRepository extends JpaRepository<Course, Long> {
    Course findById(long id);

    //Cursos gratuitos
    @Query("SELECT c FROM Course c WHERE c.price = 0")
    List<Course> findFreeCourses();

    //Cursos por id de categoría
@Query("SELECT c FROM Course c WHERE c.category.name = :categoryName")
List<Course> findByCategoryName(@Param("categoryName") String categoryName);

}