package com.example.pal.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pal.model.Course;
import com.example.pal.model.Enrollment;
import com.example.pal.model.User;
import com.example.pal.repository.CourseRepository;
import com.example.pal.repository.EnrollmentRepository;
import com.example.pal.repository.PaymentRepository;
import com.example.pal.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class EnrollmentService {

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    public Enrollment registerEnrollment(Long userId, Long courseId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con id: " + userId));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException("Curso no encontrado con id: " + courseId));

        if (enrollmentRepository.existsByUserInCourse(userId, courseId)) {
            throw new IllegalStateException("El usuario ya está inscrito en este curso");
        }

        if (course.getPrice() > 0) {
            boolean hasPaid = paymentRepository.hasUserPaidForCourse(userId, courseId);
            if (!hasPaid) {
                throw new IllegalStateException("El curso tiene un costo y el usuario no ha realizado el pago");
            }
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setUser(user);
        enrollment.setCourse(course);
        enrollment.setEnrollmentDate(LocalDate.now());

        return enrollmentRepository.save(enrollment);
    }

    public List<Course> getCoursesByUser(Long userId) {
        return enrollmentRepository.findCoursesByUser(userId);
    }

}
