package com.example.pal.service;

import com.example.pal.model.Exam;
import com.example.pal.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExamService {

    @Autowired
    ExamRepository examRepository;

    public Exam findByID(Long examId){
        return  examRepository.findExamByID(examId);
    }
}
