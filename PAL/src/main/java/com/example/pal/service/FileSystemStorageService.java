package com.example.pal.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.pal.model.File;
import com.example.pal.repository.ContentRepository;
import com.example.pal.repository.FileRepository;

import jakarta.annotation.PostConstruct;

@Service
public class FileSystemStorageService implements StorageService {

    @Value("${media.location}")
    private String mediaLocation;

    private Path rootLocation;

    @Autowired
    ContentRepository contentRepository;

    private final FileRepository fileRepository;

    public FileSystemStorageService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    @PostConstruct
    public void init() throws IOException {
        rootLocation = Paths.get(mediaLocation);
        Files.createDirectories(rootLocation);
    }

    @Override
    public String store(MultipartFile multipartFile, Long contentId) {
        if (multipartFile.isEmpty()) {
            throw new RuntimeException("Failed to store empty file " + multipartFile.getOriginalFilename());
        }

        String filename = multipartFile.getOriginalFilename();

        Path destinationFile = rootLocation.resolve(Paths.get(filename)).normalize().toAbsolutePath();

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);

            // Crear y guardar el objeto File
            File file = new File();

            // Guardar en la base de datos (el `file_url` se generará automáticamente en `@PrePersist`)
            fileRepository.save(file);

            return file.getFileUrl();
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file " + filename, e);
        }
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource((file.toUri()));

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Failed to read file " + filename);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Failed to read file " + filename, e);
        }
    }
    @Override
    public File findById(Long fileId) {
        return fileRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("File not found with id: " + fileId));
    }

    @Override
    public void delete(Long fileId) {
        File file = findById(fileId);
        
        try {
            // Obtener el nombre del archivo desde la URL
            String fileUrl = file.getFileUrl();
            String filename = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
            
            // Eliminar el archivo físico
            Path filePath = rootLocation.resolve(filename);
            Files.deleteIfExists(filePath);
            
            // Eliminar el registro de la base de datos
            fileRepository.delete(file);
        } catch (IOException e) {
            throw new RuntimeException("Failed to delete file with id: " + fileId, e);
        }
    }
}
