package com.example.pal.controller;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pal.dto.EnrollmentProgressDTO;
import com.example.pal.model.Enrollment;
import com.example.pal.repository.EnrollmentRepository;

@RestController
@RequestMapping("/api/report")
public class ProgressController {
    
    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @GetMapping("/progress/{courseId}")
    public List<EnrollmentProgressDTO> getProgressReport(@PathVariable Long courseId) {
        List<Enrollment> enrollments = enrollmentRepository.findAll();

        Random random = new Random();

        return enrollments.stream().map(enrollment -> {
            String studentName = enrollment.getUser().getName();
            String courseTitle = enrollment.getCourse().getTitle();

            int progress = random.nextInt(101);

            return new EnrollmentProgressDTO(
                    enrollment.getId(),
                    studentName,
                    courseTitle,
                    progress
            );
        }).collect(Collectors.toList());
    }
}
