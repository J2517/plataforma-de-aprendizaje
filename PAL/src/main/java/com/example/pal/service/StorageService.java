package com.example.pal.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import com.example.pal.model.File;

public interface StorageService {
    void init() throws Exception;

    String store(MultipartFile file, Long contentId);

    Resource loadAsResource(String filename);

    File findById(Long fileId);
    
    void delete(Long fileId);
}
