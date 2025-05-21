package com.example.pal.controller;

import com.example.pal.dto.ExamSubmissionDTO;
import com.example.pal.model.ExamAttempt;
import com.example.pal.service.ExamAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/exams/")
public class ExamAttemptController {

    @Autowired
    ExamAttemptService examAttemptService;

    @PostMapping("submit/{examId}")
    public ResponseEntity<ExamAttempt> submitExam(@PathVariable("examId") Long examId, @RequestBody
    ExamSubmissionDTO submission){
        ExamAttempt examAttempt = examAttemptService.registerAttempt(submission, examId);
        return ResponseEntity.status(202).body(examAttempt);
    }

    @GetMapping("results/{examId}")
    public ResponseEntity<ExamAttempt> getResult(@PathVariable("examId") Long examId){
        ExamAttempt examResult = (ExamAttempt) examAttemptService.getExamResult(examId);
        return ResponseEntity.status(202).body(examResult);
    }
}