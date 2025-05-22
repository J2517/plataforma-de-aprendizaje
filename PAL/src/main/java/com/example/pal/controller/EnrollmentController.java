package com.example.pal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.pal.dto.EnrollmentDTO;
import com.example.pal.model.Course;
import com.example.pal.model.Enrollment;
import com.example.pal.service.EnrollmentService;

@RestController
@RequestMapping("/api/enrollments")

public class EnrollmentController {

    @Autowired
    private EnrollmentService enrollmentService;

    @PostMapping("/register")
    public ResponseEntity<Enrollment> register(@RequestBody EnrollmentDTO enrollmentDTO) {
        Enrollment enrollment = enrollmentService.registerEnrollment(enrollmentDTO.getUserId(), enrollmentDTO.getCourseId());
        return ResponseEntity.status(201).body(enrollment);
    }

    @GetMapping("/my-courses")
    public ResponseEntity<List<Course>> getCoursesByUser(@RequestParam("userId") Long userId) {
        List<Course> courses = enrollmentService.getCoursesByUser(userId);
        return ResponseEntity.ok(courses);
    }

}
