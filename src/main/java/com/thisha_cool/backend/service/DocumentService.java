package com.thisha_cool.backend.service;
import com.thisha_cool.backend.dto.ChunkMetadata;
import com.thisha_cool.backend.entity.Document;
import com.thisha_cool.backend.entity.DocumentChunk;
import com.thisha_cool.backend.repository.DocumentRepository;
import com.thisha_cool.backend.repository.DocumentChunkRepository;
import org.apache.tika.Tika;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class DocumentService {
    private final ChunkService chunkService;
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
            QdrantService qdrantService,
            ChunkService chunkService
    ) {
        this.documentRepository = documentRepository;
        this.chunkRepository = chunkRepository;
        this.embeddingService = embeddingService;
        this.qdrantService = qdrantService;
        this.chunkService = chunkService;
    }

    public void saveDocumentAndChunks(String fileName,
                                      String filePath,
                                      List<ChunkMetadata> chunks){
        Document document = Document.builder()
                .fileName(fileName)
                .filePath(filePath)
                .uploadedAt(LocalDateTime.now())
                .build();

        document = documentRepository.save(document);

        int chunkNumber = 1;

        for (ChunkMetadata chunk : chunks) {

            // Save chunk in PostgreSQL
            DocumentChunk documentChunk = DocumentChunk.builder()
                    .document(document)
                    .chunkNumber(chunkNumber++)
                    .chunkText(chunk.getText())
                    .build();

            DocumentChunk savedChunk = chunkRepository.save(documentChunk);

            // Generate embedding
            List<Float> embedding =
                    embeddingService.generateEmbedding(chunk.getText());

            // Store embedding in Qdrant
            String response = qdrantService.storeEmbedding(
                    savedChunk.getId().intValue(),
                    chunk.getText(),
                    savedChunk.getDocument().getFileName(),
                    chunk.getPageNumber(),
                    String.valueOf(chunk.getParagraphNumber()),
                    savedChunk.getDocument().getUploadedAt().toString(),
                    embedding
            );

            System.out.println("Qdrant response: " + response);
        }
    }
    public List<ChunkMetadata> extractChunksWithPageNumbers(String filePath) throws Exception {

        List<ChunkMetadata> allChunks = new ArrayList<>();

        File file = new File(filePath);

        try (PDDocument document = Loader.loadPDF(file)) {



            int totalPages = document.getNumberOfPages();

            for (int page = 1; page <= totalPages; page++) {
                PDFTextStripper stripper = new PDFTextStripper();
                stripper.setStartPage(page);
                stripper.setEndPage(page);

                String pageText = stripper.getText(document);

                List<ChunkMetadata> pageChunks =
                        chunkService.createChunks(pageText);

                int paragraph = 1;

                for (ChunkMetadata chunk : pageChunks) {

                    chunk.setPageNumber(page);
                    chunk.setParagraphNumber(paragraph++);

                    allChunks.add(chunk);
                }
            }
        }

        return allChunks;
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