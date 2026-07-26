package com.example.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class InspectionService {

    private static final String UPLOAD_DIR = "inspection_reports/";

    public void saveInspection(String mouldId, MultipartFile file) throws Exception {

        Path uploadPath = Paths.get(UPLOAD_DIR);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String filename = mouldId + "_" + file.getOriginalFilename();

        Path filePath = uploadPath.resolve(filename);

        Files.copy(file.getInputStream(), filePath);
    }
}