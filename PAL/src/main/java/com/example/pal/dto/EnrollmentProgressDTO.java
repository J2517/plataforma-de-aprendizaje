package com.example.pal.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class EnrollmentProgressDTO {
    private Long enrollmentId;
    private String studentName;
    private String courseTitle;
    private int progressPercentage;
}
