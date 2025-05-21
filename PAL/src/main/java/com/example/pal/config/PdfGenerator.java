package com.example.pal.config;

import com.example.pal.model.Certificate;
import java.io.File;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.stereotype.Component;

import com.example.pal.model.Certificate;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

@Component
public class PdfGenerator {

    private static final String OUTPUT_DIR = "certificates/";
    private static final String LOGO_PATH = "src/main/resources/static/logo.png"; // cambia según la ubicación del logo

    public String generate(Certificate cert) {
        try {
            File dir = new File(OUTPUT_DIR);
            if (!dir.exists()) dir.mkdirs();

            String fileName = "cert-" + cert.getStudent().getId() + "-" + cert.getCourse().getId() + ".pdf";
            String filePath = OUTPUT_DIR + fileName;

            PdfWriter writer = new PdfWriter(filePath);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            Paragraph title = new Paragraph("Certificado de Finalización")
                    .setFontSize(24)
                    .setBold()
                    .setTextAlignment(TextAlignment.CENTER);
            document.add(title);

            document.add(new Paragraph("\n"));

            String content = String.format(
                    "Se certifica que %s ha completado satisfactoriamente el curso \"%s\".\n\n" +
                            "Fecha de emisión: %s",
                    cert.getStudent().getUsername(),
                    cert.getCourse().getTitle(),
                    cert.getIssueDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            );

            Paragraph paragraph = new Paragraph(content)
                    .setFontSize(14)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setMarginTop(30);
            document.add(paragraph);

            // Pie de página
            document.add(new Paragraph("\n\n\n____________________\nPlataforma Educativa")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(12)
                    .setFontColor(ColorConstants.GRAY));

            document.close();
            return filePath;

        } catch (IOException e) {
            throw new RuntimeException("Error al generar el PDF del certificado", e);
        }
    }
}