package br.com.seplag.edital.controller; // Ajuste o pacote se necessário

import br.com.seplag.edital.facade.FotoPessoaFacade;
import br.com.seplag.edital.model.FotoPessoa;
// --- Imports para Paginação ---
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort;
// --- Fim Imports Paginação ---
// --- Imports para OpenAPI/Swagger ---
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
// --- Fim Imports OpenAPI ---
import org.slf4j.Logger;        // Importar Logger
import org.slf4j.LoggerFactory; // Importar LoggerFactory
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// Remover import List se não for mais usado
// import java.util.List;

@RestController
@RequestMapping("/api/v1/foto-pessoas") // Padronizar com /api/v1/ e nome kebab-case
@Tag(name = "Fotos de Pessoas", description = "Operações relacionadas a Fotos de Pessoas")
public class FotoPessoaController {

    private static final Logger logger = LoggerFactory.getLogger(FotoPessoaController.class); // Adicionar Logger

    @Autowired
    private FotoPessoaFacade fotoPessoaFacade;

    // --- MÉTODO GET LISTAR PAGINADO ---
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Lista todas as fotos de pessoas de forma paginada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Página de fotos retornada",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Page.class))) // Esquema agora é Page
    })
    public ResponseEntity<Page<FotoPessoa>> listarFotoPessoas(
            // Adiciona o parâmetro Pageable com valores padrão
            // Ex: ordena pela data da foto descendente (mais recentes primeiro)
            @PageableDefault(size = 10, sort = "fpData", direction = Sort.Direction.DESC) Pageable pageable) {

        logger.info("Requisição GET /foto-pessoas com paginação: {}", pageable);
        // Chama o método paginado da Facade
        Page<FotoPessoa> paginaFotoPessoas = fotoPessoaFacade.listarFotoPessoas(pageable);
        return ResponseEntity.ok(paginaFotoPessoas); // Retorna o objeto Page
    }
    // --- FIM MÉTODO GET LISTAR PAGINADO ---

    // --- OUTROS ENDPOINTS CRUD (GET por ID, POST, PUT, DELETE) ---
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Busca uma foto de pessoa pelo ID")
    @ApiResponses(value = { /* ... */ })
    public ResponseEntity<FotoPessoa> obterFotoPessoaPorId(
            @Parameter(description = "ID da foto", required = true, example = "5")
            @PathVariable Integer id) {
        logger.debug("Requisição GET /foto-pessoas/{}", id);
        FotoPessoa fotoPessoa = fotoPessoaFacade.obterFotoPessoaPorId(id);
        if (fotoPessoa != null) {
            return ResponseEntity.ok(fotoPessoa);
        } else {
            logger.warn("FotoPessoa não encontrada com ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Cria uma nova foto de pessoa (Metadado)")
    @ApiResponses(value = { /* ... */ })
    public ResponseEntity<FotoPessoa> criarFotoPessoa(
            @Parameter(description="Objeto JSON da foto a ser criada", required = true,
                    schema=@Schema(implementation = FotoPessoa.class))
            @RequestBody FotoPessoa fotoPessoa) { // Adicionar @Valid se tiver validações
        // Nota: Este endpoint cria apenas o metadado. O upload real é feito em outro endpoint.
        logger.info("Requisição POST /foto-pessoas - Criando Metadado");
        FotoPessoa novaFotoPessoa = fotoPessoaFacade.criarFotoPessoa(fotoPessoa);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaFotoPessoa);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Atualiza metadados de uma foto de pessoa existente")
    @ApiResponses(value = { /* ... */ })
    public ResponseEntity<FotoPessoa> atualizarFotoPessoa(
            @Parameter(description = "ID da foto a ser atualizada", required = true, example = "5")
            @PathVariable Integer id,
            @Parameter(description="Objeto JSON com os dados atualizados da foto", required = true,
                    schema=@Schema(implementation = FotoPessoa.class))
            @RequestBody FotoPessoa fotoPessoa) { // Adicionar @Valid se tiver validações
        logger.info("Requisição PUT /foto-pessoas/{} - Atualizando Metadado", id);
        FotoPessoa fotoPessoaAtualizada = fotoPessoaFacade.atualizarFotoPessoa(id, fotoPessoa);
        if (fotoPessoaAtualizada != null) {
            return ResponseEntity.ok(fotoPessoaAtualizada);
        } else {
            logger.warn("Tentativa de atualizar FotoPessoa não encontrada com ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta metadados de uma foto de pessoa pelo ID")
    @ApiResponses(value = { /* ... */ })
    public ResponseEntity<Void> deletarFotoPessoa(
            @Parameter(description = "ID da foto a ser deletada", required = true, example = "5")
            @PathVariable Integer id) {
        // ATENÇÃO: Isso deleta apenas o registro no banco. O arquivo no MinIO continua existindo!
        // Você precisaria adicionar lógica para deletar o arquivo no MinIO também, se desejado.
        logger.info("Requisição DELETE /foto-pessoas/{}", id);
        boolean deletado = fotoPessoaFacade.deletarFotoPessoa(id);
        if (deletado) {
            logger.info("Metadado FotoPessoa deletado com ID: {}", id);
            return ResponseEntity.noContent().build();
        } else {
            logger.warn("Tentativa de deletar FotoPessoa não encontrada com ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }
}