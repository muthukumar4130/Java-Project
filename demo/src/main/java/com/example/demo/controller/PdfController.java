package com.example.demo.controller;

import com.example.demo.constants.URLConstants;
import com.example.demo.entity.PDFEntity;
import com.example.demo.payload.response.InputFile;
import com.example.demo.service.impl.PdfServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(URLConstants.PDF_API)
public class PdfController {

    @Autowired
    private PdfServiceImpl pdfServiceImpl;

    @PutMapping("/savePdf")
    public ResponseEntity<?> savePdfDetails(@RequestBody PDFEntity pdf){
        PDFEntity pdf1= pdfServiceImpl.savePdfDetails(pdf);
        return ResponseEntity.ok(pdf1);
    }

    @GetMapping("/getPdf")
    public ResponseEntity<?> getPdf(){
        List<PDFEntity> pdf=pdfServiceImpl.getPdfs();
        return ResponseEntity.ok(pdf);
    }

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public List<InputFile> addFile(@RequestParam("files")MultipartFile[] files){
        return pdfServiceImpl.addFile(files);
    }

}
