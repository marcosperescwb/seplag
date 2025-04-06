package br.com.seplag.edital.controller; // Ajuste o pacote se necessário

import br.com.seplag.edital.facade.PessoaFacade;
import br.com.seplag.edital.model.Pessoa;
// --- Imports para Paginação ---
import org.springframework.dao.EmptyResultDataAccessException;
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
import jakarta.validation.Valid; // Para validação
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
@RequestMapping("/api/v1/pessoas") // Padronizar path base
@Tag(name = "Pessoas", description = "Operações relacionadas a Pessoas")
public class PessoaController {

    private static final Logger logger = LoggerFactory.getLogger(PessoaController.class); // Adicionar Logger

    @Autowired
    private PessoaFacade pessoaFacade;

    // --- MÉTODO GET LISTAR PAGINADO ---
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Lista todas as pessoas de forma paginada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Página de pessoas retornada",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Page.class))) // Esquema é Page
    })
    public ResponseEntity<Page<Pessoa>> listarTodasPessoas(
            // Adiciona o parâmetro Pageable com valores padrão
            @PageableDefault(size = 10, sort = "nome", direction = Sort.Direction.ASC) Pageable pageable) { // Ordena por nome por padrão

        logger.info("Requisição GET /pessoas com paginação: {}", pageable);
        // Chama o método paginado da Facade
        Page<Pessoa> paginaPessoas = pessoaFacade.listarTodasPessoas(pageable);
        return ResponseEntity.ok(paginaPessoas); // Retorna o objeto Page
    }
    // --- FIM MÉTODO GET LISTAR PAGINADO ---

    // --- OUTROS ENDPOINTS CRUD (POST, GET por ID, PUT, DELETE) ---
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Cria uma nova pessoa")
    @ApiResponses(value = { /* ... */ })
    public ResponseEntity<Pessoa> criarPessoa(
            @Parameter(description="Objeto JSON da pessoa a ser criada", required = true,
                    schema=@Schema(implementation = Pessoa.class))
            @Valid @RequestBody Pessoa pessoa) {
        logger.info("Requisição POST /pessoas - Criando");
        Pessoa novaPessoa = pessoaFacade.criarPessoa(pessoa);
        // TODO: Retornar header Location
        return ResponseEntity.status(HttpStatus.CREATED).body(novaPessoa);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Busca uma pessoa pelo ID")
    @ApiResponses(value = { /* ... */ })
    public ResponseEntity<Pessoa> buscarPessoa(
            @Parameter(description = "ID da pessoa", required = true, example = "101")
            @PathVariable Integer id) {
        logger.debug("Requisição GET /pessoas/{}", id);
        Pessoa pessoa = pessoaFacade.buscarPessoaPorId(id);
        if (pessoa != null) {
            return ResponseEntity.ok(pessoa);
        } else {
            logger.warn("Pessoa não encontrada com ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Altera uma pessoa existente")
    @ApiResponses(value = { /* ... */ })
    public ResponseEntity<Pessoa> alterarPessoa(
            @Parameter(description = "ID da pessoa a ser alterada", required = true, example = "101")
            @PathVariable Integer id,
            @Parameter(description="Objeto JSON com os dados atualizados da pessoa", required = true,
                    schema=@Schema(implementation = Pessoa.class))
            @Valid @RequestBody Pessoa pessoa) {
        logger.info("Requisição PUT /pessoas/{} - Atualizando", id);
        Pessoa pessoaAtualizada = pessoaFacade.alterarPessoa(id, pessoa);
        if (pessoaAtualizada != null) {
            return ResponseEntity.ok(pessoaAtualizada);
        } else {
            logger.warn("Tentativa de atualizar Pessoa não encontrada com ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui uma pessoa pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pessoa excluída com sucesso", content = @Content),
            @ApiResponse(responseCode = "404", description = "Pessoa não encontrada para exclusão", content = @Content)
    })
    public ResponseEntity<Void> excluirPessoa(
            @Parameter(description = "ID da pessoa a ser excluída", required = true, example = "101")
            @PathVariable Integer id) {
        logger.info("Requisição DELETE /pessoas/{}", id);
        try {
            pessoaFacade.excluirPessoa(id); // Chama o método void
            logger.info("Pessoa excluída com ID: {}", id);
            return ResponseEntity.noContent().build(); // Retorna 204 se nenhuma exceção foi lançada
        } catch (EmptyResultDataAccessException e) { // Captura a exceção específica
            logger.warn("Tentativa de deletar Pessoa não encontrada com ID: {} - {}", id, e.getMessage());
            return ResponseEntity.notFound().build(); // Retorna 404 se a exceção foi lançada
        } catch (Exception e) { // Captura outras exceções inesperadas
            logger.error("Erro inesperado ao excluir pessoa com ID {}: {}", id, e.getMessage());
            // Pode retornar 500 ou outro status apropriado
            return ResponseEntity.internalServerError().build();
        }
    }
}