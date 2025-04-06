package br.com.seplag.edital.controller;

import br.com.seplag.edital.facade.LotacaoFacade;
import br.com.seplag.edital.model.Lotacao;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("api/v1//lotacoes")
@Tag(name = "Lotações", description = "Operações relacionadas a Lotações (alocação de servidores)")
public class LotacaoController {

    @Autowired
    private LotacaoFacade lotacaoFacade;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Lista todas as lotações")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de lotações retornada",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = List.class))) // Inferred List<Lotacao>
    })
    public ResponseEntity<Page<Lotacao>> listarLotacoes(
            @PageableDefault(size = 10, sort = "logradouro", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Lotacao> lotacoes = lotacaoFacade.listarLotacoes(pageable);
        return new ResponseEntity<>(lotacoes, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Busca uma lotação pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lotação encontrada",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Lotacao.class))),
            @ApiResponse(responseCode = "404", description = "Lotação não encontrada", content = @Content)
    })
    public ResponseEntity<Lotacao> obterLotacaoPorId(
            @Parameter(description = "ID da lotação", required = true, example = "25")
            @PathVariable Integer id) {
        Lotacao lotacao = lotacaoFacade.obterLotacaoPorId(id);
        if (lotacao != null) {
            return new ResponseEntity<>(lotacao, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Cria uma nova lotação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Lotação criada com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Lotacao.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content)
    })
    public ResponseEntity<Lotacao> criarLotacao(
            @Parameter(description="Objeto JSON da lotação a ser criada", required = true,
                    schema=@Schema(implementation = Lotacao.class))
            @RequestBody Lotacao lotacao) {
        // Adicionar @Valid se houver validações na classe Lotacao
        Lotacao novaLotacao = lotacaoFacade.criarLotacao(lotacao);
        return new ResponseEntity<>(novaLotacao, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Atualiza uma lotação existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lotação atualizada com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Lotacao.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content),
            @ApiResponse(responseCode = "404", description = "Lotação não encontrada", content = @Content)
    })
    public ResponseEntity<Lotacao> atualizarLotacao(
            @Parameter(description = "ID da lotação a ser atualizada", required = true, example = "25")
            @PathVariable Integer id,
            @Parameter(description="Objeto JSON com os dados atualizados da lotação", required = true,
                    schema=@Schema(implementation = Lotacao.class))
            @RequestBody Lotacao lotacao) {
        // Adicionar @Valid se houver validações na classe Lotacao
        Lotacao lotacaoAtualizada = lotacaoFacade.atualizarLotacao(id, lotacao);
        if (lotacaoAtualizada != null) {
            return new ResponseEntity<>(lotacaoAtualizada, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deleta uma lotação pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Lotação deletada com sucesso", content = @Content),
            @ApiResponse(responseCode = "404", description = "Lotação não encontrada", content = @Content)
    })
    public ResponseEntity<Void> deletarLotacao(
            @Parameter(description = "ID da lotação a ser deletada", required = true, example = "25")
            @PathVariable Integer id) {
        boolean deletado = lotacaoFacade.deletarLotacao(id);
        if (deletado) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}