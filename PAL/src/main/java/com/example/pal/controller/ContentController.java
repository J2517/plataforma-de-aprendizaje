package com.example.pal.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pal.dto.ContentDTO;
import com.example.pal.dto.CreateContentDTO;
import com.example.pal.model.Content;
import com.example.pal.service.ContentService;


@RestController
@RequestMapping("/api/contents")
public class ContentController {

    @Autowired
    private ContentService contentService;

    @PostMapping("/create")
    public ResponseEntity<Content> createContent(@RequestBody CreateContentDTO contentDTO) {
        Content content = contentService.createContent(contentDTO);
        return ResponseEntity.status(201).body(content);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ContentDTO>> getAllContents() {
        return ResponseEntity.ok(contentService.getAllContents());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Content> getContentById(@PathVariable("id") Long id) {
        Optional<Content> content = contentService.getContentById(id);
        return content.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Content> updateContent(@PathVariable("id") Long id, @RequestBody CreateContentDTO contentDTO) {
        Content updatedContent = contentService.updateContent(id, contentDTO);
        return ResponseEntity.ok(updatedContent);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteContent(@PathVariable("id") Long id) {
        contentService.deleteContent(id);
        return ResponseEntity.noContent().build();
    }
}
