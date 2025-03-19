package com.example.pal.dto;

import java.util.Set;

import com.example.pal.enums.ContentType;

import lombok.Data;

@Data
public class ContentDTO {
    private Long id;
    private ContentType type;
    private Set<CourseDTO> courses;
    private Set<FileDTO> files;
}