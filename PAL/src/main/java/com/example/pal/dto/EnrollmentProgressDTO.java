package com.example.pal.dto;

import lombok.Data;

@Data
public class EnrollmentProgressDTO {
    private Long enrollmentId;
    private String studentName;
    private String courseTitle;
    private int progressPercentage;
}
