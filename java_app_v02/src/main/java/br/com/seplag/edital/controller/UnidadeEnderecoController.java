package br.com.seplag.edital.controller; // Ajuste o pacote se necessário

import br.com.seplag.edital.facade.UnidadeEnderecoFacade;
import br.com.seplag.edital.model.UnidadeEndereco;
import br.com.seplag.edital.model.UnidadeEnderecoId; // Importar IdClass
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


@RestController
@RequestMapping("/api/v1/unidade-enderecos") // Padronizar com /api/v1/ e kebab-case
@Tag(name = "Unidade Endereços", description = "Associação entre Unidades e Endereços")
public class UnidadeEnderecoController {

    private static final Logger logger = LoggerFactory.getLogger(UnidadeEnderecoController.class);

    @Autowired
    private UnidadeEnderecoFacade unidadeEnderecoFacade;

    // --- MÉTODO GET LISTAR PAGINADO ---
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Lista todas as associações Unidade-Endereço de forma paginada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Página de associações retornada",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Page.class)))
    })
    public ResponseEntity<Page<UnidadeEndereco>> listarUnidadeEnderecos(
            // CORREÇÃO: Ordenação padrão deve referenciar campos de Unidade ou Endereco
            // Exemplo: Ordenar pelo nome da unidade e depois pelo logradouro do endereço
            @PageableDefault(size = 10, sort = {"unidade.unidNome", "endereco.logradouro"}, direction = Sort.Direction.ASC) Pageable pageable) {

        logger.info("Requisição GET /unidade-enderecos com paginação: {}", pageable);
        Page<UnidadeEndereco> paginaUnidadeEnderecos = unidadeEnderecoFacade.listarUnidadeEnderecos(pageable);
        // CORREÇÃO: Status deve ser OK (200) para listagem bem-sucedida
        return ResponseEntity.ok(paginaUnidadeEnderecos); // Usar .ok() para status 200
    }
    // --- FIM MÉTODO GET LISTAR PAGINADO ---

    // --- OUTROS ENDPOINTS (GET por ID composta, POST, DELETE) ---
    @GetMapping(value = "/unidade/{unidId}/endereco/{endId}", produces = MediaType.APPLICATION_JSON_VALUE) // Path mais descritivo
    @Operation(summary = "Busca uma associação Unidade-Endereço pela chave composta")
    @ApiResponses(value = { /* ... */ })
    public ResponseEntity<UnidadeEndereco> obterUnidadeEnderecoPorId(
            @Parameter(description = "ID da Unidade", required = true, example = "50")
            @PathVariable Integer unidId,
            @Parameter(description = "ID do Endereço", required = true, example = "15")
            @PathVariable Integer endId) {
        logger.debug("Requisição GET /unidade-enderecos/unidade/{}/endereco/{}", unidId, endId);
        UnidadeEndereco unidadeEndereco = unidadeEnderecoFacade.obterUnidadeEnderecoPorId(unidId, endId);
        if (unidadeEndereco != null) {
            return ResponseEntity.ok(unidadeEndereco);
        } else {
            logger.warn("Associação UnidadeEndereco não encontrada para Unidade ID: {} e Endereco ID: {}", unidId, endId);
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Cria uma nova associação Unidade-Endereço")
    @ApiResponses(value = { /* ... */ })
    public ResponseEntity<?> criarUnidadeEndereco( // Usar ResponseEntity<?> para poder retornar erro customizado
                                                   @Parameter(description="Objeto JSON da associação. Precisa incluir 'unidade': {'unidId': id} e 'endereco': {'id': id}", required = true,
                                                           schema=@Schema(implementation = UnidadeEndereco.class))
                                                   @RequestBody UnidadeEndereco unidadeEndereco) { // Adicionar @Valid se tiver validações
        // Validação de IDs (mantida da sua implementação do Service implicitamente)
        if (unidadeEndereco.getUnidade() == null || unidadeEndereco.getUnidade().getUnidId() == null ||
                unidadeEndereco.getEndereco() == null || unidadeEndereco.getEndereco().getId() == null) {
            logger.warn("Requisição POST /unidade-enderecos inválida: IDs de Unidade e Endereço são obrigatórios no corpo.");
            return ResponseEntity.badRequest().body("IDs de Unidade e Endereço são obrigatórios.");
        }

        logger.info("Requisição POST /unidade-enderecos - Criando associação para Unidade ID: {} e Endereco ID: {}",
                unidadeEndereco.getUnidade().getUnidId(), unidadeEndereco.getEndereco().getId());

        try {
            UnidadeEndereco novaUnidadeEndereco = unidadeEnderecoFacade.criarUnidadeEndereco(unidadeEndereco);
            if(novaUnidadeEndereco == null) {
                // Isso acontece se a Unidade ou Endereço não existirem (conforme sua lógica no Service)
                logger.warn("Não foi possível criar associação: Unidade ID {} ou Endereco ID {} não encontrados.",
                        unidadeEndereco.getUnidade().getUnidId(), unidadeEndereco.getEndereco().getId());
                // Retornar 404 ou 400? 404 (ou 422 Unprocessable Entity) talvez seja mais apropriado
                // pois as entidades referenciadas não existem.
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Unidade ou Endereço especificado não encontrado.");
            }
            // TODO: Retornar header Location
            return ResponseEntity.status(HttpStatus.CREATED).body(novaUnidadeEndereco);
        } catch (Exception e) {
            // Captura outros erros (ex: violação de constraint se a associação já existir)
            logger.error("Erro ao criar associação UnidadeEndereco: {}", e.getMessage());
            // Retorna 409 Conflict se for violação de constraint, ou 500 para outros erros
            if (e.getMessage() != null && e.getMessage().contains("ConstraintViolationException")) { // Verificar a exceção exata
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Associação já existente ou violação de constraint.");
            }
            return ResponseEntity.internalServerError().body("Erro ao processar a requisição.");
        }
    }

    @DeleteMapping("/unidade/{unidId}/endereco/{endId}") // Path mais descritivo
    @Operation(summary = "Deleta uma associação Unidade-Endereço pela chave composta")
    @ApiResponses(value = { /* ... */ })
    public ResponseEntity<Void> deletarUnidadeEndereco(
            @Parameter(description = "ID da Unidade", required = true, example = "50")
            @PathVariable Integer unidId,
            @Parameter(description = "ID do Endereço", required = true, example = "15")
            @PathVariable Integer endId) {
        logger.info("Requisição DELETE /unidade-enderecos/unidade/{}/endereco/{}", unidId, endId);
        boolean deletado = unidadeEnderecoFacade.deletarUnidadeEndereco(unidId, endId);
        if (deletado) {
            logger.info("Associação UnidadeEndereco deletada para Unidade ID: {} e Endereco ID: {}", unidId, endId);
            return ResponseEntity.noContent().build();
        } else {
            logger.warn("Tentativa de deletar associação UnidadeEndereco não encontrada para Unidade ID: {} e Endereco ID: {}", unidId, endId);
            return ResponseEntity.notFound().build();
        }
    }
}