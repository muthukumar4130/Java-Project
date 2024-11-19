package com.example.demo.service.impl;

import com.example.demo.entity.PDFEntity;
import com.example.demo.payload.request.FileDto;
import com.example.demo.payload.response.InputFile;
import com.example.demo.repository.PdfRepository;
import com.example.demo.service.PdfService;
import com.example.demo.utils.DataBucketUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
@Log4j2
public class PdfServiceImpl implements PdfService {

    @Autowired
    private PdfRepository pdfRepository;

    @Autowired
    private DataBucketUtil dataBucketUtil;

    @Override
    public PDFEntity savePdfDetails(PDFEntity pdf) {
        return pdfRepository.save(pdf);
    }

    @Override
    public List<PDFEntity> getPdfs() {
        return pdfRepository.findAll();
    }

    @Override
    public List<InputFile> addFile(MultipartFile[] files) {
        List<InputFile> inputFiles = new ArrayList<>();

        Arrays.asList(files).forEach(file -> {
            String fileName = file.getOriginalFilename();
            if (fileName == null || file.isEmpty()) {
                throw new RuntimeException("File is missing or empty.");
            }

            Path path = new File(fileName).toPath();
            try {
                String contentType = Files.probeContentType(path);
                FileDto fileDto = dataBucketUtil.uploadFile(file, fileName, contentType);

                if (fileDto != null) {
                    PDFEntity pdfEntity = new PDFEntity();
                    pdfEntity.setFileName(fileDto.getFileName());
                    assert fileDto.getFileUrl() != null;
                    pdfEntity.setFileUrl(fileDto.getFileUrl());

                    pdfRepository.save(pdfEntity);

                    inputFiles.add(new InputFile(fileDto.getFileName(), fileDto.getFileUrl()));
                }
            } catch (IOException e) {
                throw new RuntimeException("Error reading the file content type: " + e.getMessage(), e);
            } catch (Exception ex) {
                throw new RuntimeException("Error uploading the file: " + ex.getMessage(), ex);
            }
        });

        return inputFiles;
    }

}
