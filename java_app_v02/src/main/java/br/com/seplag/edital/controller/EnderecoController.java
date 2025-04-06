package br.com.seplag.edital.controller; // Ajuste pacote

import br.com.seplag.edital.facade.EnderecoFacade;
import br.com.seplag.edital.model.Endereco;
// Imports para Paginação
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
// Imports para OpenAPI/Swagger
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
// Imports Logging
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// Outros imports Spring
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
// Imports para validação (se usar @Valid)
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/v1/enderecos") // Padronizar path base
@Tag(name = "Endereços", description = "Operações relacionadas a Endereços")
public class EnderecoController {

    private static final Logger logger = LoggerFactory.getLogger(EnderecoController.class);

    @Autowired
    private EnderecoFacade enderecoFacade;

    // --- GET LISTAR PAGINADO ---
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Lista todos os endereços de forma paginada",
            parameters = {
                    @Parameter(name = "page", description = "Número da página (base 0)", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "0")),
                    @Parameter(name = "size", description = "Tamanho da página", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "10")),
                    // Campos de ordenação devem ser da entidade Endereco
                    @Parameter(name = "sort", description = "Ordenação pelos campos de Endereco (logradouro, bairro, cep, id, cidade.nome). Ex: bairro,desc", in = ParameterIn.QUERY, schema = @Schema(type = "string", defaultValue = "logradouro,asc"))
            })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Página de endereços retornada",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content)
    })
    public ResponseEntity<Page<Endereco>> listarEnderecos(
            @PageableDefault(size = 10, sort = "logradouro", direction = Sort.Direction.ASC)
            @Parameter(hidden = true) Pageable pageable) {

        logger.info("Requisição GET /enderecos com paginação: {}", pageable);
        Page<Endereco> paginaEnderecos = enderecoFacade.listarEnderecos(pageable);
        return ResponseEntity.ok(paginaEnderecos);
    }
    // --- FIM GET LISTAR PAGINADO ---

    // --- GET ENDERECO POR NOME DE SERVIDOR ---
    @GetMapping(value = "/por-nome-servidor", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Busca endereços (paginados) das unidades por nome parcial de servidor",
            description = "Retorna os endereços das unidades onde servidores efetivos (com nome contendo o termo de busca) estão atualmente lotados.",
            parameters = {
                    @Parameter(name = "nomeServidor", description = "Parte do nome do servidor para busca (case-insensitive)", required = true, in = ParameterIn.QUERY, schema = @Schema(type = "string", example = "Silva")),
                    @Parameter(name = "page", description = "Número da página (base 0)", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "0")),
                    @Parameter(name = "size", description = "Tamanho da página", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "10")),
                    // A ordenação aqui deve ser pelos campos de Endereco, pois é o que a query retorna
                    @Parameter(name = "sort", description = "Ordenação pelos campos de Endereco. Ex: logradouro,desc", in = ParameterIn.QUERY, schema = @Schema(type = "string", defaultValue = "logradouro,asc"))
            })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Página de endereços retornada",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "400", description = "Parâmetro 'nomeServidor' ausente ou vazio", content = @Content(schema = @Schema(type="string"))),
            @ApiResponse(responseCode = "401", description = "Não autorizado (Token inválido ou ausente)", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor", content = @Content(schema = @Schema(type="string")))
    })
    public ResponseEntity<?> getEnderecosPorNomeServidor(
            @RequestParam String nomeServidor,
            // Ordenação padrão correta (por campo de Endereco)
            @PageableDefault(size = 10, sort = "logradouro", direction = Sort.Direction.ASC)
            @Parameter(hidden = true) Pageable pageable) {

        logger.info("Requisição GET /enderecos/por-nome-servidor - nomeServidor: '{}', Paginação: {}", nomeServidor, pageable);
        // Validação do parâmetro (boa prática manter)
        if (nomeServidor == null || nomeServidor.trim().isEmpty()) {
            logger.warn("Parâmetro 'nomeServidor' está vazio ou nulo.");
            return ResponseEntity.badRequest().body("O parâmetro 'nomeServidor' é obrigatório.");
        }
        try {
            Page<Endereco> paginaEnderecos = enderecoFacade.findEnderecoUnidadePorNomeServidor(nomeServidor, pageable);
            return ResponseEntity.ok(paginaEnderecos);
        } catch (Exception e) {
            logger.error("Erro ao buscar endereços por nome de servidor '{}': {}", nomeServidor, e.getMessage(), e);
            return ResponseEntity.internalServerError().body("Erro ao processar busca por nome de servidor.");
        }
    }
    // --- FIM NOVO ENDPOINT ---

    // --- GET por ID ---
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Busca um endereço pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço encontrado", content = @Content(schema = @Schema(implementation = Endereco.class))),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado", content = @Content)
    })
    public ResponseEntity<Endereco> obterEnderecoPorId(
            @Parameter(description = "ID do endereço", required = true, example = "10")
            @PathVariable Integer id) {
        logger.debug("Requisição GET /enderecos/{}", id);
        Endereco endereco = enderecoFacade.obterEnderecoPorId(id);
        if (endereco != null) {
            return ResponseEntity.ok(endereco);
        } else {
            logger.warn("Endereço não encontrado com ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    // --- POST (Criar) ---
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Cria um novo endereço")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Endereço criado com sucesso", content = @Content(schema = @Schema(implementation = Endereco.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida (ex: dados inválidos)", content = @Content),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content)
    })
    public ResponseEntity<Endereco> criarEndereco(
            @Parameter(description="Objeto JSON do endereço a ser criado", required = true, schema=@Schema(implementation = Endereco.class))
            @Valid @RequestBody Endereco endereco) { // Adicionado @Valid
        logger.info("Requisição POST /enderecos - Criando");
        Endereco novoEndereco = enderecoFacade.criarEndereco(endereco);
        // TODO: Retornar header Location
        return ResponseEntity.status(HttpStatus.CREATED).body(novoEndereco);
    }

    // --- PUT (Alterar) ---
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Atualiza um endereço existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso", content = @Content(schema = @Schema(implementation = Endereco.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida (ex: dados inválidos)", content = @Content),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado", content = @Content)
    })
    public ResponseEntity<Endereco> atualizarEndereco(
            @Parameter(description = "ID do endereço a ser atualizado", required = true, example = "10")
            @PathVariable Integer id,
            @Parameter(description="Objeto JSON com os dados atualizados", required = true, schema=@Schema(implementation = Endereco.class))
            @Valid @RequestBody Endereco endereco) { // Adicionado @Valid
        logger.info("Requisição PUT /enderecos/{} - Atualizando", id);
        // A lógica de buscar e atualizar está na implementação do Service/Facade
        Endereco enderecoAtualizado = enderecoFacade.atualizarEndereco(id, endereco);
        if (enderecoAtualizado != null) {
            return ResponseEntity.ok(enderecoAtualizado);
        } else {
            logger.warn("Tentativa de atualizar Endereço não encontrado com ID: {}", id);
            return ResponseEntity.notFound().build();
        }
    }

    // --- DELETE ---
    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta um endereço pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Endereço deletado com sucesso", content = @Content),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado", content = @Content)
    })
    public ResponseEntity<Void> deletarEndereco(
            @Parameter(description = "ID do endereço a ser deletado", required = true, example = "10")
            @PathVariable Integer id) {
        logger.info("Requisição DELETE /enderecos/{}", id);
        boolean deletado = enderecoFacade.deletarEndereco(id); // Usa o boolean
        if (deletado) {
            logger.info("Endereço deletado com ID: {}", id);
            return ResponseEntity.noContent().build(); // 204
        } else {
            logger.warn("Tentativa de deletar Endereço não encontrado com ID: {}", id);
            return ResponseEntity.notFound().build(); // 404
        }
    }
}