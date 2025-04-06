package br.com.seplag.edital.controller; // Ajuste o pacote se necessário

import br.com.seplag.edital.facade.UnidadeFacade;
import br.com.seplag.edital.model.Unidade;
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
@RequestMapping("/api/v1/unidades") // Padronizar path base
@Tag(name = "Unidades", description = "Operações relacionadas a Unidades Organizacionais/Administrativas")
public class UnidadeController {

    private static final Logger logger = LoggerFactory.getLogger(UnidadeController.class); // Adicionar Logger

    @Autowired
    private UnidadeFacade unidadeFacade;

    // --- MÉTODO GET LISTAR PAGINADO ---
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Lista todas as unidades de forma paginada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Página de unidades retornada",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Page.class))) // Esquema é Page
    })
    public ResponseEntity<Page<Unidade>> listarUnidades(
            // Adiciona o parâmetro Pageable com valores padrão
            // Ordena por 'unidNome' (o nome do campo na entidade Unidade)
            @PageableDefault(size = 10, sort = "unidNome", direction = Sort.Direction.ASC) Pageable pageable) {

        logger.info("Requisição GET /unidades com paginação: {}", pageable);
        // Chama o método paginado da Facade
        Page<Unidade> paginaUnidades = unidadeFacade.listarUnidades(pageable);
        return ResponseEntity.ok(paginaUnidades); // Retorna o objeto Page
    }
    // --- FIM MÉTODO GET LISTAR PAGINADO ---

    // --- OUTROS ENDPOINTS CRUD (GET por ID, POST, PUT, DELETE) ---
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Busca uma unidade pelo ID")
    @ApiResponses(value = { /* ... */ })
    public ResponseEntity<Unidade> obterUnidadePorId(
            @Parameter(description = "ID da unidade", required = true, example = "50")
            @PathVariable Integer id) {
        logger.debug("Requisição GET /unidades/{}", id);
        Unidade unidade = unidadeFacade.obterUnidadePorId(id);
        if (unidade != null) {
            return ResponseEntity.ok(unidade);
        } else {
            logger.warn("Unidade não encontrada com ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Cria uma nova unidade")
    @ApiResponses(value = { /* ... */ })
    public ResponseEntity<Unidade> criarUnidade(
            @Parameter(description="Objeto JSON da unidade a ser criada", required = true,
                    schema=@Schema(implementation = Unidade.class))
            @RequestBody Unidade unidade) { // Adicionar @Valid se tiver validações
        logger.info("Requisição POST /unidades - Criando");
        Unidade novaUnidade = unidadeFacade.criarUnidade(unidade);
        // TODO: Retornar header Location
        return ResponseEntity.status(HttpStatus.CREATED).body(novaUnidade);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Atualiza uma unidade existente")
    @ApiResponses(value = { /* ... */ })
    public ResponseEntity<Unidade> atualizarUnidade(
            @Parameter(description = "ID da unidade a ser atualizada", required = true, example = "50")
            @PathVariable Integer id,
            @Parameter(description="Objeto JSON com os dados atualizados da unidade", required = true,
                    schema=@Schema(implementation = Unidade.class))
            @RequestBody Unidade unidade) { // Adicionar @Valid se tiver validações
        logger.info("Requisição PUT /unidades/{} - Atualizando", id);
        // Correção na lógica de atualização para evitar sobrescrever campos não enviados
        Unidade unidadeExistente = unidadeFacade.obterUnidadePorId(id);
        if (unidadeExistente == null) {
            logger.warn("Tentativa de atualizar Unidade não encontrada com ID: {}", id);
            return ResponseEntity.notFound().build();
        }
        // Atualiza apenas os campos permitidos (ex: nome, sigla - NUNCA o ID)
        unidadeExistente.setUnidNome(unidade.getUnidNome());
        unidadeExistente.setUnidSigla(unidade.getUnidSigla());
        // Chama o método do facade que SALVA (geralmente criar/salvar fazem o mesmo no JpaRepository)
        Unidade unidadeAtualizada = unidadeFacade.criarUnidade(unidadeExistente); // Ou facade.atualizar(id, existente) se for diferente
        return ResponseEntity.ok(unidadeAtualizada);

        // Lógica original tinha potencial de apagar campos se o RequestBody não os incluísse
        // Unidade unidadeAtualizada = unidadeFacade.atualizarUnidade(id, unidade);
        // if (unidadeAtualizada != null) {
        //     return ResponseEntity.ok(unidadeAtualizada);
        // } else {
        //     logger.warn("Tentativa de atualizar Unidade não encontrada com ID: {}", id);
        //     return ResponseEntity.notFound().build();
        // }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta uma unidade pelo ID")
    @ApiResponses(value = { /* ... */ })
    public ResponseEntity<Void> deletarUnidade(
            @Parameter(description = "ID da unidade a ser deletada", required = true, example = "50")
            @PathVariable Integer id) {
        logger.info("Requisição DELETE /unidades/{}", id);
        boolean deletado = unidadeFacade.deletarUnidade(id);
        if (deletado) {
            logger.info("Unidade deletada com ID: {}", id);
            return ResponseEntity.noContent().build();
        } else {
            logger.warn("Tentativa de deletar Unidade não encontrada com ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }
}