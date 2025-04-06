package br.com.seplag.edital.controller;

import br.com.seplag.edital.facade.CidadeFacade;
import br.com.seplag.edital.model.Cidade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cidades")
@Tag(name = "Cidades", description = "Operações relacionadas a Cidades") // Agrupa endpoints no Swagger UI
public class CidadeController {

    @Autowired
    private CidadeFacade cidadeFacade;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Cria uma nova cidade", description = "Registra uma nova cidade no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cidade criada com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Cidade.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida (ex: dados da cidade faltando ou incorretos)",
                    content = @Content) // Pode adicionar um schema de erro padrão se tiver
    })
    public ResponseEntity<Cidade> criarCidade(
            @Parameter(description="Objeto JSON da cidade a ser criada", required = true,
                    schema=@Schema(implementation = Cidade.class))
            @Valid @RequestBody Cidade cidade) {
        Cidade novaCidade = cidadeFacade.criarCidade(cidade);
        return new ResponseEntity<>(novaCidade, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Busca uma cidade pelo ID", description = "Retorna os detalhes de uma cidade específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cidade encontrada",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Cidade.class))),
            @ApiResponse(responseCode = "404", description = "Cidade não encontrada", content = @Content)
    })
    public ResponseEntity<Cidade> buscarCidade(
            @Parameter(description = "ID da cidade a ser buscada", required = true, example = "1")
            @PathVariable Integer id) {
        Cidade cidade = cidadeFacade.buscarCidadePorId(id);
        if (cidade != null) {
            return new ResponseEntity<>(cidade, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<Page<Cidade>> listarTodasCidades(
            // Parâmetros de filtro (opcionais)
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String estado,
            // Paginação (com padrões)
            @PageableDefault(size = 20, sort = "nome", direction = Sort.Direction.ASC) Pageable pageable) {
        // Chama o método da Facade que agora aceita Pageable
        Page<Cidade> paginaCidades = cidadeFacade.listarCidades(nome, estado, pageable);
        return ResponseEntity.ok(paginaCidades);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui uma cidade pelo ID", description = "Remove uma cidade do sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cidade excluída com sucesso", content = @Content),
            // Idealmente, o facade lançaria uma exceção para Not Found, que seria tratada globalmente.
            // Se o facade não lança exceção e apenas não exclui, o 204 ainda pode ser retornado.
            // Se quiser refletir a possibilidade de 404 explicitamente aqui:
            @ApiResponse(responseCode = "404", description = "Cidade não encontrada para exclusão", content = @Content)
    })
    public ResponseEntity<Void> excluirCidade(
            @Parameter(description = "ID da cidade a ser excluída", required = true, example = "1")
            @PathVariable Integer id) {
        // Adicionar lógica para verificar se foi excluído ou lançar exceção no facade
        // para poder retornar 404 se necessário. Por enquanto, segue o código original.
        cidadeFacade.excluirCidade(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Altera uma cidade existente", description = "Atualiza os dados de uma cidade específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cidade alterada com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Cidade.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content),
            @ApiResponse(responseCode = "404", description = "Cidade não encontrada", content = @Content)
    })
    public ResponseEntity<Cidade> alterarCidade(
            @Parameter(description = "ID da cidade a ser alterada", required = true, example = "1")
            @PathVariable Integer id,
            @Parameter(description="Objeto JSON com os dados atualizados da cidade", required = true,
                    schema=@Schema(implementation = Cidade.class))
            @Valid @RequestBody Cidade cidade) {
        Cidade cidadeAtualizada = cidadeFacade.alterarCidade(id, cidade);
        if (cidadeAtualizada != null) {
            return new ResponseEntity<>(cidadeAtualizada, HttpStatus.OK);
        } else {
            // O ideal seria o facade lançar uma exceção de 'NotFound'
            // que um @ControllerAdvice trataria para retornar 404.
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}