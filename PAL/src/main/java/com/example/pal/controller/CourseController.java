package com.example.pal.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.pal.dto.CourseDTO;
import com.example.pal.model.Course;
import com.example.pal.service.CourseService;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @PostMapping("/create")
    public ResponseEntity<Course> createCourse(@RequestBody CourseDTO courseDTO) {
        Course course = courseService.createCourse(courseDTO);
        return ResponseEntity.status(201).body(course);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CourseDTO>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable("id") Long id) {
        Optional<Course> course = courseService.getCourseById(id);
        return course.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable("id") Long id, @RequestBody CourseDTO courseDTO) {
        Course updatedCourse = courseService.updateCourse(id, courseDTO);
        return ResponseEntity.ok(updatedCourse);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable("id") Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/free")
    public ResponseEntity<List<Course>> getFreeCourses() {
        List<Course> freeCourses = courseService.getFreeCourses();
        if (freeCourses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(freeCourses);
    }

    @GetMapping("/search")
    public ResponseEntity<List<CourseDTO>> searchCourses(
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "isFree", required = false) Boolean isFree,
            @RequestParam(name = "level", required = false) String level,
            @RequestParam(name = "minNote", required = false) Double minNote,
            @RequestParam(name = "orderBy", defaultValue = "date") String orderBy) {

        List<CourseDTO> courses = courseService.searchCoursesWithFilters(keyword, isFree, level, minNote, orderBy);

        if (courses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(courses);
    }

}
