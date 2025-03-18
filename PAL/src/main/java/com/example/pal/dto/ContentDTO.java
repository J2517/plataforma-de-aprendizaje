package com.example.pal.dto;


import java.util.List;

import com.example.pal.enums.ContentType;

import lombok.Data;

@Data
public class ContentDTO {
    private Long id;
    private ContentType type;
    private List<Long> courses;
    private String[] files;
}
