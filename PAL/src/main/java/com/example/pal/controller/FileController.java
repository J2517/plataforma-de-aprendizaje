package com.example.pal.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.pal.model.File;
import com.example.pal.service.StorageService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/files")
@AllArgsConstructor
public class FileController {

    private final StorageService storageService;
    private final HttpServletRequest request;

    @PostMapping("upload")
    public Map<String, String> uploadFile(
            @RequestParam("file") MultipartFile multipartFile,
            @RequestParam("contentId") Long contentId) {
        
        String path = storageService.store(multipartFile, contentId);
        String host = request.getRequestURL().toString().replace(request.getRequestURI(), "");
        String url = ServletUriComponentsBuilder
                .fromHttpUrl(host)
                .path("/media/")
                .path(path)
                .toUriString();
        return Map.of("url", url);
    }

    @GetMapping("/view/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable("filename") String filename) throws IOException {
        Resource file = storageService.loadAsResource(filename);
        String contentType = Files.probeContentType(file.getFile().toPath());

        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .body(file);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<File> findById(@PathVariable("id") Long fileId) {
        File file = storageService.findById(fileId);
        return ResponseEntity.ok(file);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteFile(@PathVariable("id") Long fileId) {
        storageService.delete(fileId);
        return ResponseEntity.ok(Map.of("message", "File with id " + fileId + " was deleted successfully"));
    }
}