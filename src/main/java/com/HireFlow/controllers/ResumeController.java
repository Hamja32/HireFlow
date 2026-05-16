package com.HireFlow.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.HireFlow.entity.User;
import com.HireFlow.repository.UserRepo;
import com.HireFlow.service.FileStorageService;

@RestController
public class ResumeController {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private UserRepo userRepo;

    // Resume upload
    @PreAuthorize("hasAuthority('ROLE_SEEKER')")
    @PostMapping("/api/seeker/resume/upload")
    public ResponseEntity<String> uploadResume(
            @RequestParam("file") MultipartFile file,
            Authentication auth) throws IOException {

        // Sirf PDF allow karo
        if (!file.getContentType().equals("application/pdf")) {
            return ResponseEntity.badRequest()
                .body("Only PDF files allowed!");
        }

        String fileName = fileStorageService
            .saveFile(file, auth.getName());

        // User ke resume field update karo
        User user = userRepo.findByEmail(auth.getName())
            .orElseThrow(() -> new RuntimeException("User not found"));
        user.setResumeFileName(fileName);
        userRepo.save(user);

        return ResponseEntity.ok("Resume uploaded successfully!");
    }

    // Resume download
    @PreAuthorize("hasAuthority('ROLE_SEEKER')")
    @GetMapping("/api/seeker/resume/download")
    public ResponseEntity<ByteArrayResource> downloadResume(
            Authentication auth) throws IOException {

        User user = userRepo.findByEmail(auth.getName())
            .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getResumeFileName() == null) {
            return ResponseEntity.notFound().build();
        }

        Path filePath = fileStorageService
            .loadFile(user.getResumeFileName());
        byte[] data = Files.readAllBytes(filePath);
        ByteArrayResource resource = new ByteArrayResource(data);

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=" + user.getResumeFileName())
            .contentType(MediaType.APPLICATION_PDF)
            .contentLength(data.length)
            .body(resource);
    }
    
    @PreAuthorize("hasAuthority('ROLE_COMPANY')")
    @GetMapping("/api/company/resume")
    public ResponseEntity<ByteArrayResource> getResumeByEmail(
            @RequestParam String email) throws IOException {

    	System.out.println("Email received: " + email); // ← debug
        User user = userRepo.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
        System.out.println("Resume file name: " + user.getResumeFileName()); // ← debug

        if (user.getResumeFileName() == null) {
            return ResponseEntity.notFound().build();
        }

        Path filePath = fileStorageService
            .loadFile(user.getResumeFileName());
        System.out.println("File path: " + filePath.toAbsolutePath()); // ← debug
        System.out.println("File exists: " + Files.exists(filePath)); // ← debug
        byte[] data = Files.readAllBytes(filePath);
        ByteArrayResource resource = new ByteArrayResource(data);

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=resume.pdf")
            .contentType(MediaType.APPLICATION_PDF)
            .contentLength(data.length)
            .body(resource);
    }
}