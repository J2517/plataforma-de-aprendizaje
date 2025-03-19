package com.example.pal.model;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "files")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileUrl;

    @PrePersist
    public void generateFileUrl() {
        if (fileUrl == null || fileUrl.isBlank()) {
            this.fileUrl = "/media/" + System.currentTimeMillis() + "_" + UUID.randomUUID() + ".file";
        }
    }

    @ManyToMany(mappedBy = "files", fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Content> contents;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
