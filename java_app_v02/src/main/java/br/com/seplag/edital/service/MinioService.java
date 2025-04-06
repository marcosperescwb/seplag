package br.com.seplag.edital.service; // <-- AJUSTE ESTE PACOTE

// --- Novos Imports ---
import br.com.seplag.edital.model.Pessoa; // <-- AJUSTE O PACOTE
import br.com.seplag.edital.repository.PessoaRepository; // <-- AJUSTE O PACOTE
import jakarta.persistence.EntityNotFoundException; // Para erro se Pessoa não existe
// --- Fim Novos Imports ---

import br.com.seplag.edital.model.FotoPessoa;
import br.com.seplag.edital.repository.FotoPessoaRepository;
import io.minio.*;
import io.minio.errors.*;
import io.minio.http.Method;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class MinioService {

    private static final Logger logger = LoggerFactory.getLogger(MinioService.class);

    @Autowired
    private MinioClient minioClient;

    @Autowired
    private FotoPessoaRepository fotoPessoaRepository;

    @Autowired // Injetar o repositório da Pessoa
    private PessoaRepository pessoaRepository;

    @Value("${minio.bucket.name}")
    private String bucketName;

    @Value("${minio.url}")
    private String internalMinioUrl;

    @Value("${minio.public.url}")
    private String publicMinioUrl;

    @Value("${minio.presigned.url.expiry.minutes:5}")
    private int presignedUrlExpiryMinutes;

    // ... ensureBucketExists (sem alterações) ...

    @Transactional
    public String uploadFileAndSaveMetadata(MultipartFile file, Integer pesId)
            throws IOException, MinioException, EntityNotFoundException, RuntimeException, IllegalArgumentException, InvalidKeyException, NoSuchAlgorithmException {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Arquivo enviado é nulo ou vazio.");
        }
        if (pesId == null) {
            throw new IllegalArgumentException("ID da Pessoa (pesId) não pode ser nulo.");
        }

        // 1. Buscar a Entidade Pessoa
        // Assumindo que PessoaRepository tem findById e o ID da Pessoa é Long
        Pessoa pessoa = pessoaRepository.findById(pesId)
                .orElseThrow(() -> new EntityNotFoundException("Pessoa não encontrada com ID: " + pesId));
        logger.debug("Pessoa encontrada para associação: ID={}, Nome={}", pesId, pessoa.getNome()); // Ajuste se o getter for diferente

        // --- O restante é similar, mas usa o objeto 'pessoa' ---

        String originalFilename = file.getOriginalFilename();
        String fileExtension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String objectName = UUID.randomUUID().toString() + fileExtension;

        // 2. Ler os bytes do arquivo
        byte[] fileBytes = file.getBytes();

        // 3. Fazer Upload para o MinIO
        try (InputStream inputStream = new ByteArrayInputStream(fileBytes)) {
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(objectName)
                            .stream(inputStream, fileBytes.length, -1)
                            .contentType(file.getContentType())
                            .build());
            logger.info("Arquivo '{}' enviado com sucesso para MinIO como '{}'", originalFilename, objectName);
        } catch (MinioException e) {
            logger.error("Erro MinIO ao fazer upload do arquivo '{}' como '{}': {}", originalFilename, objectName, e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            logger.error("Erro inesperado durante upload MinIO do arquivo '{}' como '{}': {}", originalFilename, objectName, e.getMessage(), e);
            throw new MinioException();
        }

        // 4. Salvar Metadados no Banco de Dados (usando o objeto Pessoa)
        try {
            // Cria a entidade FotoPessoa usando o objeto Pessoa encontrado
            FotoPessoa fotoPessoa = new FotoPessoa(); // Usa construtor padrão
            fotoPessoa.setPessoa(pessoa);           // Define a Pessoa
            fotoPessoa.setFpData(LocalDate.now());   // Define a data
            fotoPessoa.setFpBucket(bucketName);      // Define o bucket
            fotoPessoa.setFpHash(objectName);        // Define o objectName como 'hash'/identificador

            FotoPessoa savedFoto = fotoPessoaRepository.save(fotoPessoa);
            logger.info("Metadados da foto salvos no banco para pes_id: {}, objectName: {}, fp_id: {}",
                    pesId, objectName, savedFoto.getId()); // Usa getId() da FotoPessoa
        } catch (Exception e) {
            logger.error("FALHA CRÍTICA: Erro ao salvar metadados no banco para pes_id: {}, objeto MinIO JÁ SALVO: {}. ERRO DB: {}",
                    pesId, objectName, e.getMessage(), e);
            throw new RuntimeException("Falha ao salvar metadados no banco após upload bem-sucedido para MinIO. Objeto: " + objectName, e);
        }

        return objectName;
    }

    public String getPresignedUrl(String objectName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException /* ... exceptions ... */ {

        try {
            // Usa o cliente único (interno) para gerar a URL
            String internalUrl = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .object(objectName)
                            .expiry(presignedUrlExpiryMinutes, TimeUnit.MINUTES)
                            .build());

            logger.debug("URL interna gerada: {}", internalUrl);

            // Substitui a base interna pela base pública
            String finalUrl = internalUrl.replaceFirst(internalMinioUrl, publicMinioUrl);

            logger.info("URL final gerada (após substituição) para '{}': {}", objectName, finalUrl);
            return finalUrl;

        } catch (Exception e) {
            logger.error("Erro ao gerar URL temporária para '{}': {}", objectName, e.getMessage(), e);
            throw e;
        }
    }

}