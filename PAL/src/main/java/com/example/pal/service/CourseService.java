package com.example.pal.service;

import java.time.LocalDate;
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
        if (courseDTO.getCategoryId() == null) {
            throw new IllegalArgumentException("El ID de la categoría no puede ser nulo");
        }
        if (courseDTO.getInstructorId() == null) {
            throw new IllegalArgumentException("El ID del instructor no puede ser nulo");
        }

        Category category = categoryRepository.findById(courseDTO.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        User instructor = userRepository.findById(courseDTO.getInstructorId())
                .orElseThrow(() -> new RuntimeException("Instructor no encontrado"));

        Course course = new Course();
        course.setTitle(courseDTO.getTitle());
        course.setDescription(courseDTO.getDescription());
        course.setPrice(courseDTO.getPrice());
        course.setLevel(courseDTO.getLevel());
        course.setNote(courseDTO.getNote());
        course.setCreatedAt(LocalDate.now());

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
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        if (courseDTO.getTitle() != null) {
            course.setTitle(courseDTO.getTitle());
        }
        if (courseDTO.getDescription() != null) {
            course.setDescription(courseDTO.getDescription());
        }
        if (courseDTO.getPrice() >= 0) {
            course.setPrice(courseDTO.getPrice());
        }
        if (courseDTO.getLevel() != null) {
            course.setLevel(courseDTO.getLevel());
        }
        if (courseDTO.getNote() != null) {
            course.setNote(courseDTO.getNote());
        }

        if (courseDTO.getCategoryId() != null) {
            Category category = categoryRepository.findById(courseDTO.getCategoryId())
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
            course.setCategory(category);
        }

        if (courseDTO.getInstructorId() != null) {
            User instructor = userRepository.findById(courseDTO.getInstructorId())
                    .orElseThrow(() -> new RuntimeException("Instructor no encontrado"));
            course.setInstructor(instructor);
        }

        return courseRepository.save(course);
    }

    public void deleteCourse (Long id) {
        courseRepository.deleteById(id);
    }

    public List<Course> getFreeCourses() {
        return courseRepository.findFreeCourses();
    }

    public List<Course> getCoursesByCategoryName(String categoryName) {
        return courseRepository.findByCategoryName(categoryName);
    }

    public List<CourseDTO> searchCoursesWithFilters(String keyword, Boolean isFree, String level, Double minNote, String orderBy) {
        List<Course> courses;

        if ("relevance".equalsIgnoreCase(orderBy)) {
            courses = courseRepository.searchCoursesByRelevance(keyword, isFree, level, minNote);
        } else {
            courses = courseRepository.searchCoursesByDate(keyword, isFree, level, minNote);
        }

        return courses.stream()
                .map(course -> modelMapper.map(course, CourseDTO.class))
                .collect(Collectors.toList());
    }
}
