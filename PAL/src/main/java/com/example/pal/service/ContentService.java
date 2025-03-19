package com.example.pal.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pal.dto.ContentDTO;
import com.example.pal.dto.CreateContentDTO;
import com.example.pal.model.Content;
import com.example.pal.model.Course;
import com.example.pal.model.File;
import com.example.pal.repository.ContentRepository;
import com.example.pal.repository.CourseRepository;
import com.example.pal.repository.FileRepository;

@Service
public class ContentService {

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private CourseRepository courseRepository;
    
    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Content createContent(CreateContentDTO createContentDTO) {
        Content content = new Content();
        content.setType(createContentDTO.getType());
        
        // Procesar cursos
        Set<Course> courses = new HashSet<>();
        if (createContentDTO.getCourses() != null) {
            for (String courseId : createContentDTO.getCourses()) {
                try {
                    Long id = Long.parseLong(courseId);
                    Course course = courseRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("Course not found with ID: " + id));
                    courses.add(course);
                } catch (NumberFormatException e) {
                    throw new RuntimeException("Invalid course ID format: " + courseId);
                }
            }
        }
        content.setCourses(courses);
        
        // Procesar archivos
        Set<File> files = new HashSet<>();
        if (createContentDTO.getFiles() != null) {
            for (String fileId : createContentDTO.getFiles()) {
                try {
                    Long id = Long.parseLong(fileId);
                    File file = fileRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("File not found with ID: " + id));
                    files.add(file);
                } catch (NumberFormatException e) {
                    throw new RuntimeException("Invalid file ID format: " + fileId);
                }
            }
        }
        content.setFiles(files);
        
        return contentRepository.save(content);
    }

    public List<ContentDTO> getAllContents() {
        List<Content> contents = contentRepository.findAll();
        return contents.stream()
                .map(content -> modelMapper.map(content, ContentDTO.class))
                .collect(Collectors.toList());
    }

    public Content getContentById(Long id) {
        return contentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Content not found with ID: " + id));
    }

    public Content updateContent(Long id, CreateContentDTO updateContentDTO) {
        Content content = contentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Content not found with ID: " + id));
        
        content.setType(updateContentDTO.getType());
        
        // Actualizar cursos
        if (updateContentDTO.getCourses() != null) {
            Set<Course> courses = new HashSet<>();
            for (String courseId : updateContentDTO.getCourses()) {
                try {
                    Long cId = Long.parseLong(courseId);
                    Course course = courseRepository.findById(cId)
                            .orElseThrow(() -> new RuntimeException("Course not found with ID: " + cId));
                    courses.add(course);
                } catch (NumberFormatException e) {
                    throw new RuntimeException("Invalid course ID format: " + courseId);
                }
            }
            content.setCourses(courses);
        }
        
        // Actualizar archivos
        if (updateContentDTO.getFiles() != null) {
            Set<File> files = new HashSet<>();
            for (String fileId : updateContentDTO.getFiles()) {
                try {
                    Long fId = Long.parseLong(fileId);
                    File file = fileRepository.findById(fId)
                            .orElseThrow(() -> new RuntimeException("File not found with ID: " + fId));
                    files.add(file);
                } catch (NumberFormatException e) {
                    throw new RuntimeException("Invalid file ID format: " + fileId);
                }
            }
            content.setFiles(files);
        }
        
        return contentRepository.save(content);
    }

    public void deleteContent(Long id) {
        contentRepository.deleteById(id);
    }
}