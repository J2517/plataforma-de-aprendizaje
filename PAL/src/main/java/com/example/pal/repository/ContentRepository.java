package com.example.pal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.pal.model.Content;

@Repository
public interface ContentRepository extends JpaRepository<Content, Long> {
    Content findById(long id);
}
