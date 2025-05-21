package com.example.pal.controller;

import com.example.pal.model.Certificate;
import com.example.pal.model.User;
import com.example.pal.service.CertificateService;
import jakarta.annotation.Resource;
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

    @PostMapping("/generate/{courseId}")
    public ResponseEntity<Certificate> generateCertificate(@PathVariable Long courseId, @AuthenticationPrincipal User user) {
        Certificate cert = certificateService.generateCertificate(courseId, user);
        return ResponseEntity.ok(cert);
    }

    @GetMapping("/download/{certificateId}")
    public ResponseEntity<Resource> downloadCertificate(@PathVariable Long certificateId) {
        Resource file = (Resource) certificateService.downloadCertificate(certificateId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.name())
                .contentType(MediaType.APPLICATION_PDF)
                .body(file);
    }
}
