package com.example.pal.model;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;
    
    @NotNull(message="El usuario debe tener al menos un rol")
    @Size(min=1,message="El usuario debe tener al menos un rol")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @JsonIgnore
    private Set<Role> roles;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "instructor")
    List<Course> courses;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    List<Enrollment> enrollments;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    List<Payment> payments;
}
