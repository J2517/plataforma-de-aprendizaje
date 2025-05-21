package com.example.pal.model;

import java.util.TreeMap;

import com.example.pal.config.TreeMapConverter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "exams")
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    @JsonIgnore
    private Course course;

    @Lob
    @Convert(converter = TreeMapConverter.class)
    private TreeMap<Integer, String> questionsMap = new TreeMap<>();

    @Lob
    @Convert(converter = TreeMapConverter.class)
    private TreeMap<Integer, String> correctAnswersMap = new TreeMap<>();
}
