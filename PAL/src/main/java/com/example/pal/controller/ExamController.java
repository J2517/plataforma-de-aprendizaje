package com.example.pal.controller;

import com.example.pal.model.Exam;
import com.example.pal.service.ExamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/exam")
public class ExamController {

    @Autowired
    ExamService examService;

    @GetMapping("/questions/{examId}")
    public ResponseEntity<Optional> getExamQuestions(@PathVariable("examId") Long examId) {
        Optional<Exam> examResult = examService.findByID(examId);
        return ResponseEntity.status(200).body(examResult);
    }
}
