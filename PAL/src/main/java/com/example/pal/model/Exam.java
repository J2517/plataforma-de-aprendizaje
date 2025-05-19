package com.example.pal.model;

import java.util.TreeMap;

import com.example.pal.config.TreeMapConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
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

    @Lob
    @Convert(converter = TreeMapConverter.class)
    private TreeMap<Integer, String> questionsMap = new TreeMap<>();

    @Lob
    @Convert(converter = TreeMapConverter.class)
    private TreeMap<Integer, String> correctAnswersMap = new TreeMap<>();
}
