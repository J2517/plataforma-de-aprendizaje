package com.example.pal.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class CourseDTO {
    private Long id;
    private String title;
    private String description;
    private int price;
    private String level;
    private Double note;
    private LocalDate createdAt;
    private Long categoryId;
    private Long instructorId;
}
