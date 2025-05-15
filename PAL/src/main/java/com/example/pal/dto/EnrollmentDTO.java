package com.example.pal.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class EnrollmentDTO {

    private Long id;
    private Long userId;
    private Long courseId;
    private LocalDate enrollmentDate;

}
