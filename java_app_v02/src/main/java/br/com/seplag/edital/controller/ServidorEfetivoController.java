package br.com.seplag.edital.controller;

import br.com.seplag.edital.facade.ServidorEfetivoFacade;
import br.com.seplag.edital.model.ServidorEfetivo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;

@RestController
@RequestMapping("/api/v1/servidoresEfetivos")
@Tag(name = "Servidores Efetivos", description = "Operações relacionadas a Servidores Efetivos")
public class ServidorEfetivoController {

    @Autowired
    private ServidorEfetivoFacade servidorEfetivoFacade;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Lista todos os servidores efetivos de forma paginada",
            parameters = {
                    @Parameter(name = "page", description = "Número da página (base 0)", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "0")),
                    @Parameter(name = "size", description = "Tamanho da página", in = ParameterIn.QUERY, schema = @Schema(type = "integer", defaultValue = "10")),
                    // Ordenar por matrícula? Ou pelo ID da pessoa (pesId)?
                    @Parameter(name = "sort", description = "Ordenação: campo (pesId, seMatricula). Ex: seMatricula,asc", in = ParameterIn.QUERY, schema = @Schema(type = "string", defaultValue = "pesId,asc"))
            })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Página de servidores efetivos retornada",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "401", description = "Não autorizado", content = @Content)
    })
    public ResponseEntity<Page<ServidorEfetivo>> listarServidoresEfetivos(
            // Definir ordenação padrão (ex: pelo ID da pessoa)
            @PageableDefault(size = 10, sort = "pesId", direction = Sort.Direction.ASC)
            @Parameter(hidden = true) Pageable pageable) {

        Page<ServidorEfetivo> paginaServidores = servidorEfetivoFacade.listarServidoresEfetivos(pageable);
        return ResponseEntity.ok(paginaServidores);
    }

    @GetMapping(value = "/{pesId}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Busca dados de servidor efetivo pelo ID da Pessoa")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Servidor Efetivo encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ServidorEfetivo.class))),
            @ApiResponse(responseCode = "404", description = "Servidor Efetivo não encontrado para o ID da Pessoa informado", content = @Content)
    })
    public ResponseEntity<ServidorEfetivo> obterServidorEfetivoPorPesId(
            @Parameter(description = "ID da Pessoa associada ao servidor efetivo", required = true, example = "101")
            @PathVariable Integer pesId) {
        ServidorEfetivo servidorEfetivo = servidorEfetivoFacade.obterServidorEfetivoPorPesId(pesId);
        if (servidorEfetivo != null) {
            return new ResponseEntity<>(servidorEfetivo, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Cria um novo registro de servidor efetivo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Servidor Efetivo criado com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ServidorEfetivo.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida (ex: ID de pessoa já existe como servidor, dados inválidos)", content = @Content)
    })
    public ResponseEntity<ServidorEfetivo> criarServidorEfetivo(
            @Parameter(description="Objeto JSON do servidor efetivo a ser criado. Deve incluir o ID da Pessoa (pesId).", required = true,
                    schema=@Schema(implementation = ServidorEfetivo.class))
            @RequestBody ServidorEfetivo servidorEfetivo) {
        // @Valid se necessário
        ServidorEfetivo novoServidorEfetivo = servidorEfetivoFacade.criarServidorEfetivo(servidorEfetivo);
        return new ResponseEntity<>(novoServidorEfetivo, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{pesId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Atualiza dados de um servidor efetivo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Servidor Efetivo atualizado com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ServidorEfetivo.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content),
            @ApiResponse(responseCode = "404", description = "Servidor Efetivo não encontrado para o ID da Pessoa informado", content = @Content)
    })
    public ResponseEntity<ServidorEfetivo> atualizarServidorEfetivo(
            @Parameter(description = "ID da Pessoa associada ao servidor efetivo a ser atualizado", required = true, example = "101")
            @PathVariable Integer pesId,
            @Parameter(description="Objeto JSON com os dados atualizados do servidor efetivo", required = true,
                    schema=@Schema(implementation = ServidorEfetivo.class))
            @RequestBody ServidorEfetivo servidorEfetivo) {
        // @Valid se necessário
        ServidorEfetivo servidorEfetivoAtualizado = servidorEfetivoFacade.atualizarServidorEfetivo(pesId, servidorEfetivo);
        if (servidorEfetivoAtualizado != null) {
            return new ResponseEntity<>(servidorEfetivoAtualizado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{pesId}")
    @Operation(summary = "Deleta o registro de um servidor efetivo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Servidor Efetivo deletado com sucesso", content = @Content),
            @ApiResponse(responseCode = "404", description = "Servidor Efetivo não encontrado para o ID da Pessoa informado", content = @Content)
    })
    public ResponseEntity<Void> deletarServidorEfetivo(
            @Parameter(description = "ID da Pessoa associada ao servidor efetivo a ser deletado", required = true, example = "101")
            @PathVariable Integer pesId) {
        boolean deletado = servidorEfetivoFacade.deletarServidorEfetivo(pesId);
        if (deletado) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}