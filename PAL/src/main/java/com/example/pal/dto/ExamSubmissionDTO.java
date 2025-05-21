package com.example.pal.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Map;

@Data
public class ExamSubmissionDTO {
    private Long id;
    private LocalDate attemptDate;
    private Long userId;
    private double score;
    private Map<Integer, String> answersMap;
}
