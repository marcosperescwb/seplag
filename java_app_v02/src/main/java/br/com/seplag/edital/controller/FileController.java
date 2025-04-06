package br.com.seplag.edital.controller;

import br.com.seplag.edital.service.MinioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/files")
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private MinioService minioService;

    @PostMapping("/upload")
    public ResponseEntity<List<Map<String, String>>> uploadFiles(
            // Aceita a lista de arquivos
            @RequestParam("files") List<MultipartFile> files,
            // Aceita o ID da pessoa (obrigatório)
            @RequestParam("pes_id") Integer pesId) {

        // Validação básica
        if (pesId == null) {
            return ResponseEntity.badRequest().body(List.of(Map.of("error", "O parâmetro 'pes_id' é obrigatório.")));
        }
        if (files == null || files.isEmpty() || files.stream().allMatch(MultipartFile::isEmpty)) {
            logger.warn("Tentativa de upload sem arquivos válidos para pes_id: {}", pesId);
            return ResponseEntity.badRequest().body(List.of(Map.of("error", "Nenhum arquivo válido foi enviado.")));
        }

        logger.info("Recebido upload de {} arquivo(s) para pes_id: {}", files.size(), pesId);

        List<Map<String, String>> results = new ArrayList<>();
        HttpStatus overallStatus = HttpStatus.CREATED;

        for (MultipartFile file : files) {
            if (file.isEmpty()) continue;

            Map<String, String> fileResult = new HashMap<>();
            String originalFilename = file.getOriginalFilename();
            fileResult.put("originalFilename", originalFilename);
            fileResult.put("pesId", String.valueOf(pesId)); // Inclui o pes_id no resultado

            try {
                logger.info("Iniciando upload e salvamento de metadados para: {} (pes_id: {})", originalFilename, pesId);
                // Chama o novo método do serviço que faz upload E salva no DB
                String objectName = minioService.uploadFileAndSaveMetadata(file, pesId);
                String temporaryUrl = minioService.getPresignedUrl(objectName);

                fileResult.put("objectName", objectName);
                fileResult.put("temporaryUrl", temporaryUrl);
                fileResult.put("status", "success");
                logger.info("Upload e metadados OK para: {}, URL: {}", originalFilename, temporaryUrl);

            } catch (Exception e) {
                logger.error("Falha no upload/salvamento de metadados de: {}. Erro: {}", originalFilename, e.getMessage(), e);
                fileResult.put("status", "failed");
                fileResult.put("error", "Falha no upload/DB: " + e.getClass().getSimpleName() + " - " + e.getMessage());
                overallStatus = HttpStatus.MULTI_STATUS;
            }
            results.add(fileResult);
        }

        boolean allFailed = !results.isEmpty() && results.stream().allMatch(r -> "failed".equals(r.get("status")));
        if (allFailed) {
            overallStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }

        return ResponseEntity.status(overallStatus).body(results);
    }

    // Endpoint opcional para obter a URL depois, se necessário
    @GetMapping("/{objectName}/temporary-url")
    public ResponseEntity<?> getTemporaryUrl(@PathVariable String objectName) {
        try {
            String url = minioService.getPresignedUrl(objectName);
            logger.info("Gerando URL temporária para o objeto: {}", objectName);
            return ResponseEntity.ok(Map.of("objectName", objectName, "temporaryUrl", url));
        } catch (Exception e) {
            logger.error("Erro ao gerar URL temporária para {}: {}", objectName, e.getMessage());
            // Verificar se o erro indica "objeto não encontrado" para dar um 404
            if (e.getMessage() != null && e.getMessage().contains("NoSuchKey")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Map.of("error", "Objeto não encontrado: " + objectName));
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Erro ao gerar URL: " + e.getMessage()));
        }
    }
}