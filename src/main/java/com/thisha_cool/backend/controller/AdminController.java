package com.thisha_cool.backend.controller;
import com.thisha_cool.backend.dto.ChunkMetadata;
import org.springframework.web.bind.annotation.*;
import com.thisha_cool.backend.service.DocumentService;
import com.thisha_cool.backend.service.DocumentProcessingService;
import com.thisha_cool.backend.service.ChunkService;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private DocumentService documentService;
    @Autowired
    private ChunkService chunkService;
    @Autowired
    private DocumentProcessingService documentProcessingService;
    @PostMapping("/scheduler")
    public String scheduler() {
        return "Scheduler API Working";
    }

    @PostMapping("/upload")

    public String uploadDocument(@RequestParam("file") MultipartFile file) {
        System.out.println("UPLOAD API HIT");

        try {

            String path = documentService.uploadFile(file);

            List<ChunkMetadata> chunks =
                    documentService.extractChunksWithPageNumbers(path);

            documentService.saveDocumentAndChunks(
                    file.getOriginalFilename(),
                    path,
                    chunks
            );

            return "Document Saved Successfully.\nChunks Created : "
                    + chunks.size();

        } catch (Exception e) {

            return e.getMessage();
        }
    }
}