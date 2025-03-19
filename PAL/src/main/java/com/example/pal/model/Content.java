package com.example.pal.model;

import java.util.List;
import java.util.Set;

import com.example.pal.enums.ContentType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "contents")
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ContentType type;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "content_courses",
        joinColumns = @JoinColumn(name = "content_id"),
        inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    @JsonIgnore
    private Set<Course> courses;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "content_files",
        joinColumns = @JoinColumn(name = "content_id"),
        inverseJoinColumns = @JoinColumn(name = "file_id")
    )
    @JsonIgnore
    private Set<File> files;

}
