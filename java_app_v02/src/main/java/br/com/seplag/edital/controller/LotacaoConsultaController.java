package br.com.seplag.edital.controller; // Ajuste o pacote se necessário

import br.com.seplag.edital.dto.ServidorLotacaoDTO; // DTO de resposta
import br.com.seplag.edital.service.LotacaoService; // Serviço injetado
import jakarta.persistence.EntityNotFoundException;
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

@RestController
@RequestMapping("/api/v1/unidades") // Path base para recursos relacionados a Unidades
@Tag(name = "Consultas de Lotação", description = "Endpoints para consultas complexas relacionadas a lotação de servidores")
public class LotacaoConsultaController {

    private static final Logger logger = LoggerFactory.getLogger(LotacaoConsultaController.class);

    @Autowired
    private LotacaoService lotacaoService; // Injeta o serviço que tem a lógica

    @GetMapping(value = "/{unidadeId}/servidores-efetivos", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Lista servidores efetivos lotados em uma unidade (paginado)",
            description = "Retorna uma página com Nome, Idade, Nome da Unidade e URL da foto (temporária) para servidores efetivos atualmente lotados na unidade especificada.",
            parameters = { // Documenta parâmetros de paginação explicitamente
                    @Parameter(name = "page", description = "Número da página solicitada (base 0)", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "0")),
                    @Parameter(name = "size", description = "Quantidade de itens por página", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "10")),
                    // A ordenação aqui se refere aos campos da entidade Pessoa, que é a base da query no Repository
                    @Parameter(name = "sort", description = "Ordenação no formato: campo_pessoa[,asc|desc]. Campos válidos: 'pessoa.nome', 'pessoa.dataNascimento'. Ex: pessoa.nome,asc", in = ParameterIn.QUERY, schema = @Schema(type = "string", defaultValue = "pessoa.nome,asc"))
            })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Página de servidores retornada com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            // Schema para Page<ServidorLotacaoDTO> (pode precisar de exemplo para clareza na UI)
                            schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "401", description = "Não autorizado - Token inválido ou ausente", content = @Content),
            @ApiResponse(responseCode = "404", description = "Unidade não encontrada com o ID fornecido", content = @Content(schema = @Schema(type="string"))), // Pode retornar a msg de erro
            @ApiResponse(responseCode = "500", description = "Erro interno inesperado no servidor", content = @Content(schema = @Schema(type="string")))
    })
    public ResponseEntity<?> getServidoresPorUnidade( // Usa ResponseEntity<?> para retornar corpo em erros
                                                      @Parameter(description = "ID da unidade organizacional a ser consultada", required = true, example = "1")
                                                      @PathVariable Integer unidadeId,
                                                      // Define o padrão de paginação no código. @Parameter hidden para não duplicar na UI.
                                                      @PageableDefault(size = 10, sort = "pessoa.nome", direction = Sort.Direction.ASC)
                                                      @Parameter(hidden = true) Pageable pageable) {

        logger.info("Recebida requisição para buscar servidores efetivos da unidade ID: {} com paginação: {}", unidadeId, pageable);
        try {
            // Chama o método do serviço que retorna Page<ServidorLotacaoDTO>
            Page<ServidorLotacaoDTO> paginaServidores = lotacaoService.findServidoresEfetivosPorUnidade(unidadeId, pageable);
            logger.info("Retornando {} servidores na página {} para unidade ID: {}", paginaServidores.getNumberOfElements(), pageable.getPageNumber(), unidadeId);
            return ResponseEntity.ok(paginaServidores); // Retorna 200 OK com a página

        } catch (EntityNotFoundException e) { // Captura erro se a Unidade não for encontrada
            logger.warn("Erro ao buscar servidores: {}", e.getMessage());
            // Retorna 404 Not Found com a mensagem da exceção
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) { // Captura outros erros inesperados
            logger.error("Erro interno crítico ao buscar servidores paginados para unidade ID {}: {}", unidadeId, e.getMessage(), e);
            // Retorna 500 Internal Server Error com uma mensagem genérica
            return ResponseEntity.internalServerError().body("Erro interno ao processar a requisição.");
        }
    }
}