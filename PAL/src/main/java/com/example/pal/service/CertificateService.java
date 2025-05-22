package com.example.pal.service;

import com.example.pal.config.PdfGenerator;
import com.example.pal.model.Certificate;
import com.example.pal.model.Exam;
import com.example.pal.model.User;
import com.example.pal.repository.CertificateRepository;
import com.example.pal.repository.ExamAttemptRepository;
import com.example.pal.repository.ExamRepository;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import com.example.pal.repository.UserRepository;

@Service
public class CertificateService {

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private ExamAttemptRepository attemptRepository;

    @Autowired
    private CertificateRepository certificateRepository;

    @Autowired
    private PdfGenerator pdfGenerator;

    @Autowired
    private UserRepository userRepository;

    public Certificate generateCertificate(Long courseId, Long studentId) {
        // Buscar al estudiante por su ID
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Estudiante no encontrado con ID: " + studentId));

        // Obtener todos los exámenes del curso
        List<Exam> exams = examRepository.findByCourseId(courseId);

        // Validar que el estudiante ha aprobado todos los exámenes
        for (Exam exam : exams) {
            var attempt = attemptRepository.findTopByExamIdAndStudentIdOrderByScoreDesc(exam.getId(), student.getId());
            if (attempt.isEmpty() || attempt.get().getScore() < 60) { // Asumiendo que 60 es el mínimo aprobatorio
                throw new IllegalArgumentException("El estudiante no ha aprobado todos los exámenes");
            }
        }

        Certificate certificate = new Certificate();
        certificate.setCourse(exams.get(0).getCourse());
        certificate.setStudent(student);
        certificate.setIssueDate(LocalDateTime.now());

        String filePath = pdfGenerator.generate(certificate);
        certificate.setFilePath(filePath);

        return certificateRepository.save(certificate);
    }

    public FileSystemResource downloadCertificate(Long certificateId) {
        Certificate cert = certificateRepository.findById(certificateId)
                .orElseThrow(() -> new RuntimeException("Certificado no encontrado"));

        return new FileSystemResource(cert.getFilePath());
    }
}
