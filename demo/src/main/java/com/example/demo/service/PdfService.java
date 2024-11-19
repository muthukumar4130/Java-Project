package com.example.demo.service;

import com.example.demo.entity.PDFEntity;
import com.example.demo.payload.response.InputFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PdfService {
    PDFEntity savePdfDetails(PDFEntity pdf);

    List<PDFEntity> getPdfs();

    List<InputFile> addFile(MultipartFile[] files);
}
