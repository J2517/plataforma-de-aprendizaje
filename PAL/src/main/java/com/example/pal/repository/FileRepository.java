package com.example.pal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pal.model.File;

@Repository
public interface FileRepository extends JpaRepository<File, Long> {
    File findById(long id);
    File findByFileUrl(String fileUrl);
}
