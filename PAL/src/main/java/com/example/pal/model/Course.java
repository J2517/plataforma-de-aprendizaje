package com.example.pal.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private String description;
    
    @PositiveOrZero(message = "El precio no puede ser negativo")
    @Column(nullable = false)
    private int price;

    @Pattern(regexp="básico|intermedio|avanzado",message="El nivel debe ser 'básico', 'intermedio' o 'avanzado'")
    @Column(nullable = false)
    private String level;

    @Min(value = 0, message = "La nota no puede ser negativa")
    @Max(value = 5, message = "La nota no puede ser mayor a 5")
    @Column(nullable = false)
    private Double note;

    @Column(nullable = false)
    private LocalDate createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "instructor_id", nullable = false)
    private User instructor;

    @ManyToMany(mappedBy = "courses", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Content> contents = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "course")
    @JsonIgnore
    List<Enrollment> enrollments;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "course")
    @JsonIgnore
    List<Payment> payments;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Course)) {
            return false;
        }
        Course course = (Course) o;
        return id != null && id.equals(course.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
