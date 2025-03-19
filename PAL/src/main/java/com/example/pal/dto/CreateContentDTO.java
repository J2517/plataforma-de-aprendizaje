package com.example.pal.dto;

import com.example.pal.enums.ContentType;

import lombok.Data;

@Data
public class CreateContentDTO {
    private ContentType type;
    private String[] courses;
    private String[] files;
}
