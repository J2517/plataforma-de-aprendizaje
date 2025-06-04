package com.example.pal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentProgressDTO {
    private Long enrollmentId;
    private String studentName;
    private String courseTitle;
    private int progressPercentage;
}
