package com.example.pal.dto;

import lombok.Data;

@Data
public class CourseDTO {
    private Long id;
    private String title;
    private String description;
    private int price;
    private Long categoryId;
    private Long instructorId;
}
