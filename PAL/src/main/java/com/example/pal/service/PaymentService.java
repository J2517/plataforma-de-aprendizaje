package com.example.pal.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.pal.dto.PaymentDTO;
import com.example.pal.model.Course;
import com.example.pal.model.Payment;
import com.example.pal.model.User;
import com.example.pal.repository.CourseRepository;
import com.example.pal.repository.PaymentRepository;
import com.example.pal.repository.UserRepository;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    public Payment createPayment(PaymentDTO paymentDTO) {
        if (paymentDTO.getUserId() == null) {
            throw new IllegalArgumentException("User no encontrado");
        }
        if (paymentDTO.getCourseId() == null) {
            throw new IllegalArgumentException("Course no encontrado");
        }

        User user = userRepository.findById(paymentDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con ID: " + paymentDTO.getUserId()));
        Course course = courseRepository.findById(paymentDTO.getCourseId())
                .orElseThrow(() -> new IllegalArgumentException("Curso no encontrado con ID: " + paymentDTO.getCourseId()));
                
        if (paymentDTO.getAmount() == null || paymentDTO.getAmount() != course.getPrice()) {
            throw new IllegalArgumentException("El monto pagado debe ser igual al precio del curso");
        }
        Payment payment = new Payment();
        payment.setAmount(paymentDTO.getAmount());
        payment.setPaymentDate(paymentDTO.getPaymentDate());
        payment.setUser(user);
        payment.setCourse(course);

        return paymentRepository.save(payment);
    }
}
