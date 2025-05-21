package com.example.pal.service;

import com.example.pal.config.PdfGenerator;
import com.example.pal.model.Certificate;
import com.example.pal.model.Exam;
import com.example.pal.model.User;
import com.example.pal.repository.CertificateRepository;
import com.example.pal.repository.ExamAttemptRepository;
import com.example.pal.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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

    public Certificate generateCertificate(Long courseId, User student) {
        List<Exam> exams = examRepository.findByCourseId(courseId);

        for (Exam exam : exams) {
            var attempt = attemptRepository.findTopByExamIdAndStudentIdOrderByScoreDesc(exam.getId(), student.getId());
            if (attempt.isEmpty() || attempt.get().getScore() < 60) { // asumiendo 60 es mínimo aprobatorio
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
