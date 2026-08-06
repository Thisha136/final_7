package com.thisha_cool.backend.service;

import com.thisha_cool.backend.entity.Document;
import com.thisha_cool.backend.entity.DocumentChunk;
import com.thisha_cool.backend.repository.DocumentRepository;
import com.thisha_cool.backend.repository.DocumentChunkRepository;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class DocumentService {

    private final DocumentRepository documentRepository;
    private final DocumentChunkRepository chunkRepository;
    private final EmbeddingService embeddingService;
    private final QdrantService qdrantService;

    private final Tika tika = new Tika();

    private static final String UPLOAD_DIR =
            System.getProperty("user.dir") + File.separator + "uploads";

    public DocumentService(
            DocumentRepository documentRepository,
            DocumentChunkRepository chunkRepository,
            EmbeddingService embeddingService,
            QdrantService qdrantService
    ) {
        this.documentRepository = documentRepository;
        this.chunkRepository = chunkRepository;
        this.embeddingService = embeddingService;
        this.qdrantService = qdrantService;
    }

    public void saveDocumentAndChunks(String fileName,
                                      String filePath,
                                      List<String> chunks) {

        Document document = Document.builder()
                .fileName(fileName)
                .filePath(filePath)
                .uploadedAt(LocalDateTime.now())
                .build();

        document = documentRepository.save(document);

        int chunkNumber = 1;

        for (String chunk : chunks) {

            // Save chunk in PostgreSQL
            DocumentChunk documentChunk = DocumentChunk.builder()
                    .document(document)
                    .chunkNumber(chunkNumber++)
                    .chunkText(chunk)
                    .build();

            DocumentChunk savedChunk = chunkRepository.save(documentChunk);

            // Generate embedding
            List<Float> embedding =
                    embeddingService.generateEmbedding(chunk);

            // Store embedding in Qdrant
            String response = qdrantService.storeEmbedding(
                    savedChunk.getId().intValue(),
                    savedChunk.getChunkText(),
                    savedChunk.getDocument().getFileName(),
                    1,      // TODO: Extract actual page number later
                    "",     // TODO: Extract paragraph metadata later
                    embedding
            );

            System.out.println("Qdrant response: " + response);
        }
    }

    public String uploadFile(MultipartFile file) throws IOException {

        File directory = new File(UPLOAD_DIR);

        if (!directory.exists() && !directory.mkdirs()) {
            throw new IOException("Failed to create upload directory");
        }

        String fileName = file.getOriginalFilename() != null
                ? file.getOriginalFilename()
                : "uploaded_file";

        File destination = new File(directory, fileName);

        file.transferTo(destination);

        return destination.getAbsolutePath();
    }

    public String extractText(String filePath) throws Exception {

        File file = new File(filePath);

        if (!file.exists()) {
            throw new IOException("File not found: " + filePath);
        }

        return tika.parseToString(file);
    }
}