package com.example.demo.repository;

import com.example.demo.entity.PDFEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PdfRepository extends JpaRepository<PDFEntity,Long> {
}
