package com.PFE.electroplanetaudit.service;

import com.PFE.electroplanetaudit.entity.ElementScore;
import com.PFE.electroplanetaudit.entity.MediaEvidence;
import com.PFE.electroplanetaudit.repository.ElementScoreRepository;
import com.PFE.electroplanetaudit.repository.MediaEvidenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class MediaEvidenceService {

    private final MediaEvidenceRepository mediaEvidenceRepository;
    private final ElementScoreRepository elementScoreRepository;

    @Value("${upload.directory:uploads/audit-photos/}")
    private String uploadDir;

    // ===== UPLOAD PHOTO =====
    public MediaEvidence uploadPhoto(Long sessionId, Long elementId, MultipartFile file) {

        // Find the element score
        ElementScore elementScore = elementScoreRepository
                .findByAuditSessionIdAndAuditElementId(sessionId, elementId);

        if (elementScore == null) {
            throw new RuntimeException("Element score not found for session " + sessionId + " and element " + elementId);
        }

        // Validate file
        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        // Validate file type (only images)
        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            throw new RuntimeException("Only image files are allowed");
        }

        // Generate unique filename
        String originalFileName = file.getOriginalFilename();
        String extension = "";
        if (originalFileName != null && originalFileName.contains(".")) {
            extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }

        String fileName = UUID.randomUUID().toString() + extension;

        // Save file to disk
        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(fileName);
            Files.write(filePath, file.getBytes());

            // Create media evidence record
            MediaEvidence media = MediaEvidence.builder()
                    .imageUrl("/uploads/audit-photos/" + fileName)
                    .fileName(originalFileName)
                    .uploadedAt(LocalDateTime.now())
                    .elementScore(elementScore)
                    .build();

            return mediaEvidenceRepository.save(media);

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file: " + e.getMessage());
        }
    }

    // ===== GET ALL PHOTOS FOR A SESSION =====
    @Transactional(readOnly = true)
    public List<MediaEvidence> getPhotosBySession(Long sessionId) {
        return mediaEvidenceRepository.findByAuditSessionId(sessionId);
    }

    // ===== GET PHOTOS FOR A SPECIFIC ELEMENT =====
    @Transactional(readOnly = true)
    public List<MediaEvidence> getPhotosByElement(Long sessionId, Long elementId) {
        ElementScore elementScore = elementScoreRepository
                .findByAuditSessionIdAndAuditElementId(sessionId, elementId);

        if (elementScore == null) {
            throw new RuntimeException("Element score not found");
        }

        return mediaEvidenceRepository.findByElementScoreId(elementScore.getId());
    }

    // ===== DELETE PHOTO =====
    public boolean deletePhoto(Long mediaId) {
        MediaEvidence media = mediaEvidenceRepository.findById(mediaId)
                .orElseThrow(() -> new RuntimeException("Photo not found"));

        // Delete file from disk
        try {
            String filePath = media.getImageUrl();
            if (filePath != null && filePath.startsWith("/uploads/")) {
                // Remove leading slash and get relative path
                String relativePath = filePath.substring(1);
                Path path = Paths.get(relativePath);
                Files.deleteIfExists(path);
            }
        } catch (IOException e) {
            System.err.println("Failed to delete file: " + e.getMessage());
        }

        mediaEvidenceRepository.delete(media);
        return true;
    }
}