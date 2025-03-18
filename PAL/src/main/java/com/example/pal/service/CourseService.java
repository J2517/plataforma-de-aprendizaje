package com.example.pal.service;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pal.dto.CourseDTO;
import com.example.pal.model.Category;
import com.example.pal.model.Course;
import com.example.pal.model.User;
import com.example.pal.repository.CategoryRepository;
import com.example.pal.repository.CourseRepository;
import com.example.pal.repository.UserRepository;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Course createCourse(CourseDTO courseDTO) {
        Category category = categoryRepository.findById(courseDTO.getCategory_id())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        User instructor = userRepository.findById(courseDTO.getInstructor_id())
                .orElseThrow(() -> new RuntimeException("Instructor no encontrado"));

        Course course = new Course();
        course.setTitle(courseDTO.getTitle());
        course.setDescription(courseDTO.getDescription());
        course.setPrice(courseDTO.getPrice());
        course.setCategory(category);
        course.setInstructor(instructor);

        return courseRepository.save(course);
    }

    public List<CourseDTO> getAllCourses() {
        List<Course> courses = courseRepository.findAll();
        return courses.stream().map(course -> modelMapper.map(course, CourseDTO.class)).collect(Collectors.toList());
    }

    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    public Course updateCourse(Long id, CourseDTO courseDTO) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Category category = categoryRepository.findById(courseDTO.getCategory_id())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        User instructor = userRepository.findById(courseDTO.getInstructor_id())
                .orElseThrow(() -> new RuntimeException("Instructor no encontrado"));

        course.setTitle(courseDTO.getTitle());
        course.setDescription(courseDTO.getDescription());
        course.setPrice(courseDTO.getPrice());
        course.setCategory(category);
        course.setInstructor(instructor);

        return courseRepository.save(course);
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

}
