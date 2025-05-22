package com.example.pal.controller;

import com.example.pal.model.Certificate;
import com.example.pal.model.User;
import com.example.pal.service.CertificateService;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/certificates")
public class CertificateController {

    @Autowired
    private CertificateService certificateService;

    @PostMapping("/generate/{courseId}/{studentId}")
    public ResponseEntity<Certificate> generateCertificate(
            @PathVariable("courseId") Long courseId,
            @PathVariable("studentId") Long studentId) {
        Certificate cert = certificateService.generateCertificate(courseId, studentId);
        return ResponseEntity.ok(cert);
    }

    @GetMapping("/download/{certificateId}")
    public ResponseEntity<org.springframework.core.io.Resource> downloadCertificate(@PathVariable("certificateId") Long certificateId) {
        FileSystemResource file = certificateService.downloadCertificate(certificateId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(file);
    }
}
