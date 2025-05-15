package com.example.pal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.pal.model.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Course findById(long id);

    //Cursos gratuitos
    @Query("SELECT c FROM Course c WHERE c.price = 0")
    List<Course> findFreeCourses();

    //Cursos por id de categoría
    @Query("SELECT c FROM Course c WHERE c.category.name = :categoryName")
    List<Course> findByCategoryName(@Param("categoryName") String categoryName);

// Ordenar por fecha (createdAt descendente)
    @Query("SELECT c FROM Course c WHERE "
            + "(:keyword IS NULL OR LOWER(c.title) LIKE LOWER(CONCAT('%', :keyword, '%')) "
            + "   OR LOWER(c.description) LIKE LOWER(CONCAT('%', :keyword, '%')) "
            + "   OR LOWER(c.category.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND "
            + "(:isFree IS NULL OR (:isFree = true AND c.price = 0) OR (:isFree = false AND c.price > 0)) AND "
            + "(:level IS NULL OR LOWER(c.level) = LOWER(:level)) AND "
            + "(:minNote IS NULL OR c.note >= :minNote) "
            + "ORDER BY c.createdAt DESC")
    List<Course> searchCoursesByDate(@Param("keyword") String keyword,
            @Param("isFree") Boolean isFree,
            @Param("level") String level,
            @Param("minNote") Double minNote);

// Ordenar por relevancia (nota descendente)
    @Query("SELECT c FROM Course c WHERE "
            + "(:keyword IS NULL OR LOWER(c.title) LIKE LOWER(CONCAT('%', :keyword, '%')) "
            + "   OR LOWER(c.description) LIKE LOWER(CONCAT('%', :keyword, '%')) "
            + "   OR LOWER(c.category.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND "
            + "(:isFree IS NULL OR (:isFree = true AND c.price = 0) OR (:isFree = false AND c.price > 0)) AND "
            + "(:level IS NULL OR LOWER(c.level) = LOWER(:level)) AND "
            + "(:minNote IS NULL OR c.note >= :minNote) "
            + "ORDER BY c.note DESC")
    List<Course> searchCoursesByRelevance(@Param("keyword") String keyword,
            @Param("isFree") Boolean isFree,
            @Param("level") String level,
            @Param("minNote") Double minNote);

}
