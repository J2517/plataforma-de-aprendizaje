package com.example.pal.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pal.dto.ContentDTO;
import com.example.pal.model.Content;
import com.example.pal.model.Course;
import com.example.pal.repository.ContentRepository;
import com.example.pal.repository.CourseRepository;

@Service
public class ContentService {

    @Autowired
    private ContentRepository contentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Content createContentWithCourses(ContentDTO contentDTO) {
        Content content = new Content();
        content.setType(contentDTO.getType());
        Set<Course> courses = new HashSet<>();

        for (Long courseId : contentDTO.getCourses()) {
            Course course = courseRepository.findById(courseId)
                    .orElseThrow(() -> new RuntimeException("Course not found with ID: " + courseId));
            courses.add(course);
        }

        content.setCourses(courses);
        return contentRepository.save(content);
    }

    public List<ContentDTO> getAllContents() {
        List<Content> contents = contentRepository.findAll();
        return contents.stream().map(content -> modelMapper.map(content, ContentDTO.class)).collect(Collectors.toList());
    }

    public Optional<Content> getContentById(Long id) {
        return contentRepository.findById(id);
    }

    public Content updateContent(Long id, Content contentDetails) {
        Content content = contentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Content not found with ID: " + id));
        content.setType(contentDetails.getType());
        return contentRepository.save(content);
    }

    public void deleteContent(Long id) {
        contentRepository.deleteById(id);
    }
}
